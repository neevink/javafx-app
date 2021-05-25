package com.neevin.Programm;

import com.neevin.Database.DatabaseConnection;
import com.neevin.Database.PasswordHasher;
import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;
import com.neevin.DataModels.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Сервис, который выполяет операции над коллекцией
 */
public class ExecutionService {
    /**
     * Все команды
     */
    protected HashMap<String, Executable> commands = new HashMap<>();
    /**
     * Коллекция
     */
    public final HashMap<Long, Route> map = new HashMap<>();

    protected DatabaseConnection dbConnection;

    public ExecutionService(DatabaseConnection dbConnection) throws Exception{
        this.dbConnection = dbConnection;

        // Регистрируем все команды, которые может выполнять сервер и назначаем им обработчики
        registerCommand("info", this::info);
        registerCommand("show", this::show);
        registerCommand("insert", this::insert);
        registerCommand("update", this::update);
        registerCommand("remove_key", this::remove);
        registerCommand("clear", this::clear);
        registerCommand("replace_if_greater", this::replaceIfGreater);
        registerCommand("replace_if_lowe", this::replaceIfLowe);
        registerCommand("remove_lower_key", this::removeLowerKey);
        registerCommand("filter_starts_with_name", this::filterStartsWithName);
        registerCommand("filter_greater_than_distance", this::filterGreaterThanDistance);
        registerCommand("print_field_ascending_distance", this::printFieldAscendingDistance);

        registerCommand("login", this::login);
        registerCommand("register", this::register);

        updateMap();
    }

    /**
     * Зарегистрировать новую команду
     * @param name название команды
     * @param func функция, которая её выполняет
     */
    protected void registerCommand(String name, Executable func){
        commands.put(name, func);
    }

    /**
     * Метод, который выполняет обновление коллекции из базы данных
     * @throws Exception
     */
    public void updateMap() throws Exception {
        ArrayList<Route> routes = new ArrayList<>();
        routes = dbConnection.getAllRoutes();

        map.clear();
        for(Route r : routes){
            map.put(r.getId(), r);
        }
    }

    /**
     * Выполнить команду (запрос)
     * @param request запрос
     * @return результат выполнения
     */
    public CommandResult executeCommand(Request<?> request){
        if(!commands.containsKey(request.command)){
            return new CommandResult(ResultStatus.ERROR, "На сервере такая команда не сществует");
        }
        return commands.get(request.command).execute(request);
    }

    protected CommandResult clear(Request<?> request){
        try{
            dbConnection.deleteAllRoutes(request.userLogin);
            updateMap();
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, exc.getMessage());
        }
        return new CommandResult(ResultStatus.OK, "Все элементы, принадлежащие этому пользователю, успешно удалены из коллекции.");
    }

    protected CommandResult filterGreaterThanDistance(Request<?> request){
        long distance;
        try{
            distance = ((Long) request.entity).longValue();
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }

        StringBuffer message = new StringBuffer();

        map.entrySet().stream()
                .map(x -> x.getValue())
                .filter(x -> x.getDistance() > distance)
                .sorted(Comparator.comparing(Route::getCoordinates).reversed())
                .forEach(x -> message.append(x.toString()));

        if(message.length() == 0){
            return new CommandResult(ResultStatus.OK,
                    "Нет ни одного объекта, поле distance которого больше заданного.");
        }

        return new CommandResult(ResultStatus.OK, message.toString());
    }

    protected CommandResult filterStartsWithName(Request<?> request){
        String name;
        try{
            name = (String) request.entity;
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }

        StringBuilder message = new StringBuilder();

        map.entrySet().stream()
                .map(x -> x.getValue())
                .filter(x -> x.getName().startsWith(name))
                .sorted(Comparator.comparing(Route::getCoordinates).reversed())
                .forEach(x -> message.append(x.toString() + '\n'));

        if(message.length() == 0){
            return new CommandResult(ResultStatus.OK,
                    "Нет ни одного объекта, поле name которого начинается с данной подстроки.");
        }

        return new CommandResult(ResultStatus.OK, message.toString());
    }

    protected CommandResult info(Request<?> request){
        String type = "HashMap<Long, Route>";
        String lang = (String) request.entity;

        if(lang == null || lang.equals("ru")){
            return new CommandResult(ResultStatus.OK,
                    "Информация: " + "\n" +
                            "Тип коллекции: " + type + "\n" +
                            "Количество элементов в коллекции: " + map.size()  + "\n" +
                            "Пользователь, под которым выполнен вход: " + request.userLogin
            );
        }
        else if(lang.equals("tr")){ // Турецкий
            return new CommandResult(ResultStatus.OK,
                    "Bilgi: " + "\n" +
                            "Koleksiyon türü: " + type + "\n" +
                            "Koleksiyondaki öğe sayısı: " + map.size()  + "\n" +
                            "Giriş yapan kullanıcı: " + request.userLogin
            );
        }
        else if(lang.equals("es")){ // Испанский
            return new CommandResult(ResultStatus.OK,
                    "Información: " + "\n" +
                            "Tipo de colección: " + type + "\n" +
                            "El número de elementos de la colección: " + map.size()  + "\n" +
                            "El usuario que inició sesión: " + request.userLogin
            );
        }
        else{ // Датский
            return new CommandResult(ResultStatus.OK,
                    "Information: " + "\n" +
                            "Samlingstype: " + type + "\n" +
                            "Antallet af genstande i samlingen: " + map.size()  + "\n" +
                            "Brugeren, der er logget ind: " + request.userLogin
            );
        }
    }

    protected CommandResult insert(Request<?> request){
        Route route;
        try{
            route = (Route) request.entity;
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }

        try{
            dbConnection.AddRoute(route, request.userLogin);
        }
        catch (SQLException exc){
            return new CommandResult(ResultStatus.ERROR, exc.getMessage());
        }

        try {
            updateMap();
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "Данные в базе данных повреждены!");
        }
        return new CommandResult(ResultStatus.OK, "Новый элемент успешно добавлен!");
    }

    protected CommandResult printFieldAscendingDistance(Request<?> request){
        if(map.size() == 0){
            return new CommandResult(ResultStatus.OK, "Коллекция пуста. Нечего выводить.");
        }

        StringBuilder message = new StringBuilder();

        map.entrySet().stream()
                .map(x -> x.getValue().getDistance())
                .sorted()
                .forEach(x -> message.append(x + "\n"));

        return new CommandResult(ResultStatus.OK, message.toString());
    }

    protected CommandResult remove(Request<?> request){
        long id;
        try{
            id = ((Long) request.entity).longValue();
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }

        try{
            dbConnection.deleteRoute(request.userLogin, id);
            updateMap();
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, exc.getMessage());
        }

        return new CommandResult(ResultStatus.OK, String.format("Элемент с индексом %d успешно удалён", id));
    }

    protected CommandResult removeLowerKey(Request<?> request){
        long id;
        try{
            id = ((Long) request.entity).longValue();
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }

        Long[] keys = map.keySet().stream()
                .map(x -> x.longValue())
                .filter(x -> x < id)
                .toArray(Long[]::new);


        int count = 0;
        for(long key : keys){
            try{
                dbConnection.deleteRoute(request.userLogin, key);
                count++;
            }
            catch (Exception exc){ }
        }

        try{
            updateMap();
        }
        catch (Exception exc){}

        return new CommandResult(ResultStatus.OK,
                String.format("Из коллекции успешно удалено %d элементов, которые принадлежат этому пользователю", count));
    }

    protected CommandResult replaceIfGreater(Request<?> request){
        Route route;
        try{
            route = (Route) request.entity;
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }

        if(!dbConnection.isOwnedByUser(request.userLogin, route.getId())){
            return new CommandResult(ResultStatus.ERROR,
                    "Элемента, принадлежащего этому пользователю, с таким индексом не существует!");
        }

        Route storedRoute;
        try{
            storedRoute = dbConnection.getRouteById(route.getId());
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, exc.getMessage());
        }

        if(route.compareTo(storedRoute) > 0){
            dbConnection.updateRoute(request.userLogin, route);
            try{
                updateMap();
            }
            catch (Exception exc){
                return new CommandResult(ResultStatus.OK, exc.getMessage());
            }
            
            return new CommandResult(ResultStatus.OK,"Новое значение больше старого. Произведена замена.");
        }
        else{
            return new CommandResult(ResultStatus.OK,
                    "Новое значение меньше или равно старому. Значение не изменено.");
        }
    }

    protected CommandResult replaceIfLowe(Request<?> request){
        Route route;
        try{
            route = (Route) request.entity;
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }

        if(!dbConnection.isOwnedByUser(request.userLogin, route.getId())){
            return new CommandResult(ResultStatus.ERROR,
                    "Элемента, принадлежащего этому пользователю, с таким индексом не существует!");
        }

        Route storedRoute;
        try{
            storedRoute = dbConnection.getRouteById(route.getId());
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, exc.getMessage());
        }

        if(route.compareTo(storedRoute) < 0){
            dbConnection.updateRoute(request.userLogin, route);
            try{
                updateMap();
            }
            catch (Exception exc){
                return new CommandResult(ResultStatus.OK, exc.getMessage());
            }

            return new CommandResult(ResultStatus.OK,"Новое значение меньше старого. Произведена замена.");
        }
        else{
            return new CommandResult(ResultStatus.OK,
                    "Новое значение больше или равно старому. Значение не изменено.");
        }
    }

    protected CommandResult show(Request<?> request){
        if(map.size() == 0){
            CommandResult<ArrayList<Route>> answer = new CommandResult(ResultStatus.OK, "Колекция пуста.");
            answer.entity = new ArrayList<Route>();
            return answer;
        }

        StringBuffer message = new StringBuffer();
        map.entrySet().stream()
                .map(x -> x.getValue())
                .sorted(Comparator.comparing(Route::getCoordinates).reversed())
                .forEach(x -> message.append(x.toString() + "\n"));

        CommandResult<ArrayList<Route>> answer = new CommandResult(ResultStatus.OK, message.toString());
        answer.entity = new ArrayList<Route>(map.values());
        return answer;
    }

    protected CommandResult update(Request<?> request){
        Route newRoute;
        try{
            newRoute = (Route) request.entity;
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }

        if(!dbConnection.isOwnedByUser(request.userLogin, newRoute.getId())){
            return new CommandResult(ResultStatus.ERROR,
                    "Элемента, принадлежащего этому пользователю, с таким индексом не существует!");
        }

        try{
            dbConnection.updateRoute(request.userLogin, newRoute);
            updateMap();
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.OK, exc.getMessage());
        }

        String message = String.format("Значение элемента с id %d успешно обновлено.", newRoute.getId());
        return new CommandResult(ResultStatus.OK, message);
    }

    protected CommandResult login(Request<?> request) {
        Account account;
        try {
            account = (Account) request.entity;
        } catch (Exception exc) {
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }

        try {
            String salt = dbConnection.getAccountSalt(account.login);
            String pwdHash = PasswordHasher.hashPassword(account.password, salt);
            dbConnection.checkAccountPasswordHash(account.login, pwdHash);
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, exc.getMessage());
        }

        return new CommandResult(ResultStatus.OK, "Вход в аккаунт выполнен успешно");
    }

    protected CommandResult register(Request<?> request) {
        Account account;
        try {
            account = (Account) request.entity;
        } catch (Exception exc) {
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }

        String salt = PasswordHasher.generateSalt();
        String hashedPwd = PasswordHasher.hashPassword(account.password, salt);

        try {
            if(dbConnection.registerAccount(account.login, hashedPwd, salt)){
                return new CommandResult(ResultStatus.OK, "Новый аккаутн успешно зарегистрирован!");
            }
            else {
                return new CommandResult(ResultStatus.ERROR, "Аккаунт с таким именем уже существует!");
            }
        }
        catch (SQLException exc){
            return new CommandResult(ResultStatus.ERROR, "Аккаунт с таким именем уже существует! " + exc.getMessage());
        }
    }
}
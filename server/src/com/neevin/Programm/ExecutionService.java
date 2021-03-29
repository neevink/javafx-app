package com.neevin.Programm;

import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;
import com.neevin.DataModels.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

// Типо сервис, который выполняет команды
public class ExecutionService {
    protected HashMap<String, Executable> commands = new HashMap<>();
    protected CollectionController controller;

    public ExecutionService(CollectionController controller){
        this.controller = controller;

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
    }

    protected void registerCommand(String name, Executable func){
        commands.put(name, func);
    }

    public CommandResult executeCommand(Request<?> request){
        if(!commands.containsKey(request.command)){
            return new CommandResult(ResultStatus.ERROR, "На сервере такая команда не сществует");
        }
        return commands.get(request.command).execute(request);
    }

    public CommandResult clear(Request<?> request){
        controller.map.clear();
        return new CommandResult(ResultStatus.OK, "Все элементы успешно удалены из коллекции.");
    }

    public CommandResult filterGreaterThanDistance(Request<?> request){
        long distance;
        try{
            distance = (long) request.entity;
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }

        boolean wasPrint = false;
        String message = "";

        for(long key : controller.map.keySet()){
            Route r = controller.map.get(key);
            if(r.getDistance() > distance){
                message += r.toString() + '\n';
                wasPrint = true;
            }
        }

        if(!wasPrint){
            return new CommandResult(ResultStatus.OK,
                    "Нет ни одного объекта, поле distance которого больше заданного.");
        }
        return new CommandResult(ResultStatus.OK, message);
    }

    public CommandResult filterStartsWithName(Request<?> request){
        String name;
        try{
            name = (String) request.entity;
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }

        if(controller.map.size() == 0){
            return new CommandResult(ResultStatus.OK,"Коллекция пуста. Нечего выводить.");
        }

        boolean wasPrint = false;
        String message = "";

        for(long key : controller.map.keySet()){
            Route r = controller.map.get(key);

            if(r.getName().startsWith(name)){
                message += r.toString() + '\n';
                wasPrint = true;
            }
        }

        if(!wasPrint){
            return new CommandResult(ResultStatus.OK,
                    "Нет ни одного объекта, поле name которого начинается с данной подстроки.");
        }
        return new CommandResult(ResultStatus.OK, message);
    }

    public CommandResult info(Request<?> request){
        String type = "HashMap<Long, Route>";

        return new CommandResult(ResultStatus.OK,
                "Информация о коллекции: " + "\n" +
                        "Тип коллекции: " + type + "\n" +
                        "Дата инициализации: " + controller.initializationTime + "\n" +
                        "Количество элементов в коллекции: " + controller.map.size()
        );
    }

    public CommandResult insert(Request<?> request){
        Route route;
        try{
            route = (Route) request.entity;
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }
        route.setId(controller.getNextId());

        controller.map.put(route.getId(), route);
        return new CommandResult(ResultStatus.OK, "Новый элемент успешно добавлен!");
    }

    public CommandResult printFieldAscendingDistance(Request<?> request){
        if(controller.map.size() == 0){
            return new CommandResult(ResultStatus.OK, "Коллекция пуста. Нечего выводить.");
        }

        // Список дистанций
        ArrayList<Long> arr = new ArrayList<Long>();
        for(long key : controller.map.keySet()){
            long distance = controller.map.get(key).getDistance();
            arr.add(distance);
        }

        String message = "";
        Collections.sort(arr);
        for(long e :arr){
            message += e + "\n";
        }

        return new CommandResult(ResultStatus.OK, message);
    }

    public CommandResult remove(Request<?> request){
        long id;
        try{
            id = (long) request.entity;
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }

        if(!controller.map.containsKey(id)){
            return new CommandResult(ResultStatus.ERROR, "Элемент с этим id не содержится в коллекции");
        }

        controller.map.remove(id);
        return new CommandResult(ResultStatus.OK, String.format("Элемент с индексом %d успешно удалён", id));
    }

    public CommandResult removeLowerKey(Request<?> request){
        long id;
        try{
            id = (long) request.entity;
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }

        ArrayList<Long> keys = new ArrayList<Long>();
        for(long key : controller.map.keySet()){
            keys.add(key);
        }

        int count = 0;
        for(long key : keys){
            Route r = controller.map.get(key);
            if(r.getId() < id){
                controller.map.remove(key);
                count++;
            }
        }

        return new CommandResult(ResultStatus.OK, String.format("Из коллекции успешно удалено %d элементов.", count));
    }

    public CommandResult replaceIfGreater(Request<?> request){
        Route route;
        try{
            route = (Route) request.entity;
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }
        if(!controller.map.containsKey(route.getId())){
            return new CommandResult(ResultStatus.ERROR,"Элемента с таким индексом не существует!");
        }

        Route storedRoute = controller.map.get(route.getId());


        if(route.compareTo(storedRoute) > 0){
            controller.map.remove(storedRoute);
            controller.map.put(route.getId(), route);

            return new CommandResult(ResultStatus.OK,"Новое значение больше старого. Произведена замена.");
        }
        else{
            return new CommandResult(ResultStatus.OK,
                    "Новое значение меньше или равно старому. Значение не изменено.");
        }
    }

    public CommandResult replaceIfLowe(Request<?> request){
        Route route;
        try{
            route = (Route) request.entity;
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }
        if(!controller.map.containsKey(route.getId())){
            return new CommandResult(ResultStatus.ERROR,"Элемента с таким индексом не существует!");
        }

        Route storedRoute = controller.map.get(route.getId());


        if(route.compareTo(storedRoute) < 0){
            controller.map.remove(storedRoute);
            controller.map.put(route.getId(), route);

            return new CommandResult(ResultStatus.OK,"Новое значение больше старого. Произведена замена.");
        }
        else{
            return new CommandResult(ResultStatus.OK,
                    "Новое значение меньше или равно старому. Значение не изменено.");
        }
    }

    public CommandResult show(Request<?> request){
        if(controller.map.size() == 0){
            return new CommandResult(ResultStatus.OK, "Коллекция пуста.");
        }

        String message = "";
        for(long key : controller.map.keySet()){
            Route r = controller.map.get(key);
            message += r.toString() + '\n';
        }
        return new CommandResult(ResultStatus.OK, message);
    }

    public CommandResult update(Request<?> request){
        Route newRoute;
        try{
            newRoute = (Route) request.entity;
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }

        if(!controller.map.containsKey(newRoute.getId())){
            return new CommandResult(ResultStatus.ERROR, "Элемента с таким индексом не существует!");
        }

        Route storedRoute = controller.map.get(newRoute.getId());
        controller.map.remove(storedRoute);
        controller.map.put(newRoute.getId(), newRoute);

        String message = String.format("Значение элемента с id %d успешно обновлено.", newRoute.getId());
        return new CommandResult(ResultStatus.OK, message);
    }
}

package com.neevin.Commands;

import com.neevin.DataModels.Coordinates;
import com.neevin.DataModels.Location;
import com.neevin.DataModels.LocationInteger;
import com.neevin.DataModels.Route;
import com.neevin.Parser.Parser;
import com.neevin.Parser.Token;
import com.neevin.Parser.Tokenizer;
import com.neevin.Programm.CollectionController;

import java.util.AbstractList;
import java.util.Scanner;

public class InsertCommand implements Command {
    CollectionController controller;

    public InsertCommand(CollectionController controller){
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент с заданным ключом.\n" +
                "\t\tinsert id \"name\" distance";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        // Перед этим было введено insert id(целое число) "name"(строка) distance(целое число)

        if(tokens == null){
            throw new IllegalArgumentException("Сисок токенов не может быть равен null!");
        }

        // Токенов должно быть 4: название команды и 3 аргумента
        if(tokens.size() != 4){
            throw new IllegalArgumentException("Аргументов этой команды должно быть 3.");
        }

        Route route = new Route();

        // Порсинг полей Route
        long id;
        try{
            id = Parser.parseLong(tokens.get(1));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента id не удался. " + e.getMessage());
        }
        route.setId(id);

        String name;
        try{
            name = Parser.parseString(tokens.get(2));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента name не удался. " + e.getMessage());
        }
        route.setName(name);

        long distance;
        try{
            distance = Parser.parseLong(tokens.get(3));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента distance не удался. " + e.getMessage());
        }
        route.setDistance(distance);

        // Порсинг полей Coordinates
        Scanner s = new Scanner(System.in);

        receiveCoordinates(route, s);

        receiveFrom(route, s);

        receiveTo(route, s);

        controller.map.put(id, route);
        System.out.println("Новый элемент успешно добавлен!\n");
    }

    private void receiveCoordinates(Route route, Scanner s) {
        boolean coordinatesParsed = false;

        System.out.println("Введите поля объекта Coordinates через пробел: x(дробное число) y(дробное число). Введите пусую строку, чтобы задать значение null.");
        // Пока координаты не спарсены успешно
        while (!coordinatesParsed){
            try{
                AbstractList<Token> coordinatesTokens = Tokenizer.tokenize(s.nextLine());
                Coordinates coordinates = null;
                if(coordinatesTokens.size() != 0) {
                    coordinates = Parser.parseCoordinates(coordinatesTokens);
                }
                route.setCoordinates(coordinates);
                coordinatesParsed = true;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Повторите ввод.");
            }
        }
    }

    private void receiveFrom(Route route, Scanner s) {
        boolean locationParsed = false;

        System.out.println("Введите поля объекта Location через пробел: x(дробное число) y(дробное число). Введите пусую строку, чтобы задать значение null.");
        // Пока координаты не спарсены успешно
        while (!locationParsed){
            try{
                AbstractList<Token> fromTokens = Tokenizer.tokenize(s.nextLine());
                Location from = null;
                if(fromTokens.size() != 0) {
                    from = Parser.parseLocation(fromTokens);
                }
                route.setFrom(from);
                locationParsed = true;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Повторите ввод.");
            }
        }
    }

    private void receiveTo(Route route, Scanner s) {
        boolean locationParsed = false;

        System.out.println("Введите поля объекта Location через пробел: x(дробное число) y(дробное число). Введите пусую строку, чтобы задать значение null.");
        // Пока координаты не спарсены успешно
        while (!locationParsed){
            try{
                AbstractList<Token> toTokens = Tokenizer.tokenize(s.nextLine());
                LocationInteger to = null;
                if(toTokens.size() != 0) {
                    to = Parser.parseLocationInteger(toTokens);
                }
                route.setTo(to);
                locationParsed = true;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Повторите ввод.");
            }
        }
    }
}

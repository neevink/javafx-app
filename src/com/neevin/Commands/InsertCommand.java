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

        // Порсинг полей Route
        long id;
        try{
            id = Parser.parseLong(tokens.get(1));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента id не удался. " + e.getMessage());
        }

        String name;
        try{
            name = Parser.parseString(tokens.get(2));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента name не удался. " + e.getMessage());
        }

        long distance;
        try{
            distance = Parser.parseLong(tokens.get(3));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента distance не удался. " + e.getMessage());
        }

        // Порсинг полей Coordinates
        Scanner s = new Scanner(System.in);
        System.out.println("Введите поля объекта Coordinates через пробел: x(дробное число) y(дробное число)");
        AbstractList<Token> coordinatesTokens = Tokenizer.tokenize(s.nextLine());
        Coordinates coordinates = Parser.parseCoordinates(coordinatesTokens);

        // Порсинг полей Location
        System.out.println("Введите поля объекта Location через пробел: x(дробное число) y(дробное число) name(строка в двойных кавычках)");
        AbstractList<Token>  locationTokens = Tokenizer.tokenize(s.nextLine());
        Location location = Parser.parseLocation(locationTokens);

        // Порсинг полей LocationInteger
        System.out.println("Введите поля объекта Location через пробел: x(целое число) y(целое число) name(строка в двойных кавычках)");
        AbstractList<Token>  locationIntegerTokens = Tokenizer.tokenize(s.nextLine());
        LocationInteger locationInteger = Parser.parseLocationInteger(locationTokens);

        Route route = new Route(id, name, coordinates, location, locationInteger, distance);
        controller.dictionary.put(id, route);

        System.out.println("Новый элемент успешно добавлен!\n");
    }
}

package com.neevin.Commands;

import com.neevin.DataModels.Coordinates;
import com.neevin.DataModels.Location;
import com.neevin.DataModels.LocationInteger;
import com.neevin.DataModels.Route;
import com.neevin.Parser.Parser;
import com.neevin.Parser.Token;
import com.neevin.Parser.Tokenizer;
import com.neevin.Programm.CollectionController;

import java.io.InputStream;
import java.util.AbstractList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class InsertCommand implements Command {
    CollectionController controller;
    Scanner scanner;

    public InsertCommand(CollectionController controller, Scanner scanner){
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент с заданным ключом / insert \"name\" distance";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        InputHelper.displayInput(tokens);

        if(tokens == null){
            throw new IllegalArgumentException("Сисок токенов не может быть равен null!");
        }

        // Токенов должно быть 3: название команды и 2 аргумента
        if(tokens.size() != 3){
            throw new IllegalArgumentException("Аргументов этой команды должно быть 2.");
        }

        Route route = new Route();

        route.setId(controller.getNextId());

        InputHelper.receiveName(route, tokens.get(1));
        InputHelper.receiveDistance(route, tokens.get(2));

        InputHelper.receiveCoordinates(route, scanner);
        InputHelper.receiveFrom(route, scanner);
        InputHelper.receiveTo(route, scanner);

        controller.map.put(route.getId(), route);
        System.out.println("Новый элемент успешно добавлен!");
    }
}

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
                "\t\tinsert \"name\" distance";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        // Перед этим было введено insert id(целое число) "name"(строка) distance(целое число)

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

        Scanner s = new Scanner(System.in);
        InputHelper.receiveCoordinates(route, s);
        InputHelper.receiveFrom(route, s);
        InputHelper.receiveTo(route, s);

        controller.map.put(route.getId(), route);
        System.out.println("Новый элемент успешно добавлен!\n");
    }
}

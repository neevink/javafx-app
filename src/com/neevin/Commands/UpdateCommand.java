package com.neevin.Commands;

import com.neevin.DataModels.Route;
import com.neevin.Parser.Token;
import com.neevin.Programm.CollectionController;

import java.util.AbstractList;
import java.util.Scanner;

public class UpdateCommand implements Command{
    CollectionController controller;

    public UpdateCommand(CollectionController controller){
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному\n"+
                "\t\tupdate id \"name\" distance";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        if(tokens == null){
            throw new IllegalArgumentException("Сисок токенов не может быть равен null!");
        }

        // Токенов должно быть 4: название команды и 3 аргумента
        if(tokens.size() != 4){
            throw new IllegalArgumentException("Аргументов этой команды должно быть 3.");
        }

        Route newRoute = new Route();

        InputHelper.receiveId(newRoute, tokens.get(1));

        if(!controller.map.containsKey(newRoute.getId())){
            throw new Exception("Элемента с таким индексом не существует!");
        }

        InputHelper.receiveName(newRoute, tokens.get(2));
        InputHelper.receiveDistance(newRoute, tokens.get(3));

        Scanner s = new Scanner(System.in);
        InputHelper.receiveCoordinates(newRoute, s);
        InputHelper.receiveFrom(newRoute, s);
        InputHelper.receiveTo(newRoute, s);

        Route storedRoute = controller.map.get(newRoute.getId());
        controller.map.remove(storedRoute);
        controller.map.put(newRoute.getId(), newRoute);

        System.out.println(String.format("Значение элемента с id %d успешно обновлено.", newRoute.getId()));
    }
}

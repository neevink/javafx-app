package com.neevin.Commands;

import com.neevin.DataModels.Route;
import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Token;
import com.neevin.Programm.CollectionController;

import java.util.AbstractList;
import java.util.Scanner;

public class ReplaceIfLoweCommand implements Command{
    CollectionController controller;
    private Scanner scanner;

    public ReplaceIfLoweCommand(CollectionController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public String getName() {
        return "replace_if_lowe";
    }

    @Override
    public String getDescription() {
        return "заменить значение по ключу, если новое значение меньше старого / replace_if_lowe id \"name\" distance";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        InputHelper.displayInput(tokens);
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

        InputHelper.receiveCoordinates(newRoute, scanner);
        InputHelper.receiveFrom(newRoute, scanner);
        InputHelper.receiveTo(newRoute, scanner);

        Route storedRoute = controller.map.get(newRoute.getId());

        if(newRoute.compareTo(storedRoute) < 0){
            controller.map.remove(storedRoute);
            controller.map.put(newRoute.getId(), newRoute);

            System.out.println("Новое значение меньше старого. Произведена замена.");
        }
        else{
            System.out.println("Новое значение больше или равно старому. Значение не изменено.");
        }
    }
}

package com.neevin.Commands;

import com.neevin.DataModels.Route;
import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Token;
import com.neevin.Programm.Connection;

import java.util.AbstractList;
import java.util.Scanner;

/**
 * Заменить значение по ключу, если новое значение больше старого
 */
public class ReplaceIfGreaterCommand implements Command{
    Connection connection;
    private Scanner scanner;

    public ReplaceIfGreaterCommand(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    @Override
    public String getName() {
        return "replace_if_greater";
    }

    @Override
    public String getDescription() {
        return "заменить значение по ключу, если новое значение больше старого / replace_if_greater id \"name\" distance";
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

        //if(!controller.map.containsKey(newRoute.getId())){
        //    throw new Exception("Элемента с таким индексом не существует!");
        //}

        InputHelper.receiveName(newRoute, tokens.get(2));
        InputHelper.receiveDistance(newRoute, tokens.get(3));

        InputHelper.receiveCoordinates(newRoute, scanner);
        InputHelper.receiveFrom(newRoute, scanner);
        InputHelper.receiveTo(newRoute, scanner);

        //ExecutionService.replaceIfGreater(newRoute);
        /*
        Route storedRoute = controller.map.get(newRoute.getId());

        if(newRoute.compareTo(storedRoute) > 0){
            controller.map.remove(storedRoute);
            controller.map.put(newRoute.getId(), newRoute);

            System.out.println("Новое значение больше старого. Произведена замена.");
        }
        else{
            System.out.println("Новое значение меньше или равно старому. Значение не изменено.");
        }

         */
    }
}

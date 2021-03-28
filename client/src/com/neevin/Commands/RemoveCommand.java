package com.neevin.Commands;

import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Parser;
import com.neevin.Parser.Token;
import com.neevin.Programm.Connection;

import java.util.AbstractList;

/**
 * Удалить элемент из коллекции
 */
public class RemoveCommand implements Command{
    Connection connection;

    public RemoveCommand(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String getName() {
        return "remove_key";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его ключу / remove_key id";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        InputHelper.displayInput(tokens);
        if(tokens == null){
            throw new IllegalArgumentException("Список токенов не может быть null!");
        }

        // Токенов команды должно быть 2: название команды и аргумент id
        if(tokens.size() != 2){
            throw new Exception("Аргументов этой команды должно быть 1. remove_key id");
        }

        // Порсинг id
        long id;
        try{
            id = Parser.parseLong(tokens.get(1));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента id не удался. " + e.getMessage());
        }

        //ExecutionService.remove(id);
        /*
        if(!controller.map.containsKey(id)){
            throw new IndexOutOfBoundsException("Элемент с этим id не содержится в коллекции");
        }

        controller.map.remove(id);
        System.out.println(String.format("Элемент с индексом %d успешно удалён", id));

         */
    }
}

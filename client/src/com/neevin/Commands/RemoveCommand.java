package com.neevin.Commands;

import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;
import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Parser;
import com.neevin.Parser.Token;
import com.neevin.Programm.RequestSender;

import java.util.AbstractList;

/**
 * Удалить элемент из коллекции
 */
public class RemoveCommand implements Command{
    RequestSender requestSender;

    public RemoveCommand(RequestSender requestSender) {
        this.requestSender = requestSender;
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

        Request<?> request = new Request<Long>(this.getName(), id, requestSender.getUserLogin(), requestSender.getUserPassword());
        CommandResult result = requestSender.sendRequest(request);

        if(result.status == ResultStatus.OK){
            System.out.println(result.message);
        }
        else{
            System.out.println("Произошла ошибка: " + result.message);
        }
    }
}

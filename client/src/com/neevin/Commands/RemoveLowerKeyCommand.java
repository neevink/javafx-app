package com.neevin.Commands;

import com.neevin.DataModels.Route;
import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;
import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Parser;
import com.neevin.Parser.Token;
import com.neevin.Programm.Connection;

import java.util.AbstractList;

/**
 * Удалить из коллекции все элементы, ключ которых меньше, чем заданный
 */
public class RemoveLowerKeyCommand implements Command{
    private Connection connection;

    public  RemoveLowerKeyCommand(Connection connection){
        this.connection = connection;
    }

    @Override
    public String getName() {
        return "remove_lower_key";
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, ключ которых меньше, чем заданный / remove_lower_key id";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        InputHelper.displayInput(tokens);
        long id;
        try{
            id = Parser.parseLong(tokens.get(1));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента distance не удался. " + e.getMessage());
        }

        Request<?> request = new Request<Long>(this.getName(), id);
        CommandResult result = connection.sendRequest(request);

        if(result.status == ResultStatus.OK){
            System.out.println(result.message);
        }
        else{
            System.out.println("Произошла ошибка: " + result.message);
        }
    }
}

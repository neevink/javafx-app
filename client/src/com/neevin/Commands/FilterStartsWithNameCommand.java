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
 * Вывести элементы, имя которых начинается со строки
 */
public class FilterStartsWithNameCommand implements Command{
    RequestSender requestSender;

    public FilterStartsWithNameCommand(RequestSender requestSender){
        this.requestSender = requestSender;
    }

    @Override
    public String getName() {
        return "filter_starts_with_name";
    }

    @Override
    public String getDescription() {
        return " вывести элементы, значение поля name которых начинается с заданной подстроки / filter_starts_with_name \"name\"";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        InputHelper.displayInput(tokens);

        String name;
        try{
            name = Parser.parseString(tokens.get(1));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента name не удался. " + e.getMessage());
        }

        Request<?> request = new Request<String>(this.getName(), name, requestSender.getUserLogin(), requestSender.getUserPassword());
        CommandResult result = requestSender.sendRequest(request);

        if(result.status == ResultStatus.OK){
            System.out.println(result.message);
        }
        else{
            System.out.println("Произошла ошибка: " + result.message);
        }
    }
}

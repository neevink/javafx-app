package com.neevin.Commands;

import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;
import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Token;
import com.neevin.Programm.RequestSender;

import java.util.AbstractList;

/**
 * Вывести значения поля distance всех элементов в порядке возрастания
 */
public class PrintFieldAscendingDistanceCommand implements Command{
    private RequestSender requestSender;

    public  PrintFieldAscendingDistanceCommand(RequestSender requestSender){
        this.requestSender = requestSender;
    }

    @Override
    public String getName() {
        return "print_field_ascending_distance";
    }

    @Override
    public String getDescription() {
        return "вывести значения поля distance всех элементов в порядке возрастания";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        InputHelper.displayInput(tokens);

        Request<?> request = new Request<Object>(this.getName(), null, requestSender.getUserLogin(), requestSender.getUserPassword());
        CommandResult result = requestSender.sendRequest(request);

        if(result.status == ResultStatus.OK){
            System.out.println(result.message);
        }
        else{
            System.out.println("Произошла ошибка: " + result.message);
        }
    }
}

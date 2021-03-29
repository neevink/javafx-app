package com.neevin.Commands;

import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;
import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Token;
import com.neevin.Programm.Connection;

import java.util.AbstractList;

/**
 * Вывести значения поля distance всех элементов в порядке возрастания
 */
public class PrintFieldAscendingDistanceCommand implements Command{
    private Connection connection;

    public  PrintFieldAscendingDistanceCommand(Connection connection){
        this.connection = connection;
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

        Request<?> request = new Request<Object>(this.getName(), null);
        CommandResult result = connection.sendRequest(request);

        if(result.status == ResultStatus.OK){
            System.out.println(result.message);
        }
        else{
            System.out.println("Произошла ошибка: " + result.message);
        }
    }
}

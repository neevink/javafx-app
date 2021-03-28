package com.neevin.Commands;

import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Token;
import com.neevin.Programm.Connection;

import java.util.AbstractList;

/**
 * Вывести информацию о коллекции
 */
public class InfoCommand implements Command{
    private Connection connection;

    public InfoCommand(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    @Override
    public void execute(AbstractList<Token> tokens) {
        InputHelper.displayInput(tokens);

        Request<?> request = new Request<String>(this.getName(), null);
        CommandResult result = connection.sendRequest(request);

        System.out.println(result.status.toString() + " : " + result.message);

        //ExecutionService.info();
        /*
        String type = "HashMap<Long, Route>";

        System.out.println(
                "Информация о коллекции: " + "\n" +
                "Тип коллекции: " + type + "\n" +
                "Дата инициализации: " + controller.initializationTime + "\n" +
                "Количество элементов в коллекции: " + controller.map.size()
        );
         */
    }
}
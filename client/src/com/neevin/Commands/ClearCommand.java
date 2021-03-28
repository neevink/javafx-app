package com.neevin.Commands;

import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Token;
import com.neevin.Programm.Connection;

import java.util.AbstractList;

/**
 *
 * Команда, очищающая коллекцию
 */
public class ClearCommand implements Command {
    private Connection connection;

    public ClearCommand(Connection connection){
        this.connection = connection;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    @Override
    public void execute(AbstractList<Token> tokens) {
        InputHelper.displayInput(tokens);

        Request<?> request = new Request<String>(this.getName(), null);
        CommandResult result = connection.sendRequest(request);
        /* Вынес в другое место
        controller.map.clear();
        System.out.println("Все элементы успешно удалены из коллекции.");

        */
    }
}

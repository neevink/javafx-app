package com.neevin.Commands;

import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Token;
import com.neevin.Programm.Connection;

import java.util.AbstractList;

/**
 * Вывести список элементов коллекции
 */
public class ShowCommand implements Command {
    private Connection connection;

    public ShowCommand(Connection connection){
        this.connection = connection;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public void execute(AbstractList<Token> tokens) {
        InputHelper.displayInput(tokens);

        //ExecutionService.show();
        /*
        if(controller.map.size() == 0){
            System.out.println("Коллекция пуста.");
        }

        for(long key : controller.map.keySet()){
            Route r = controller.map.get(key);
            System.out.println(r.toString() + '\n');
        }

         */
    }
}

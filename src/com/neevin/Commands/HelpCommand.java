package com.neevin.Commands;

import com.neevin.Parser.Token;
import com.neevin.Programm.CommandManager;

import java.util.AbstractList;

public class HelpCommand implements Command {
    CommandManager manager;

    public HelpCommand(CommandManager manager){
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }

    @Override
    public void execute(AbstractList<Token> tokens) {
        InputHelper.displayInput(tokens);
        System.out.println(manager.getCommandsNameWithDescription());
    }
}
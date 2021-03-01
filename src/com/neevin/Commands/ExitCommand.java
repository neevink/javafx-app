package com.neevin.Commands;

import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Token;

import java.util.AbstractList;

public class ExitCommand implements Command {
    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "завершить программу (без сохранения в файл)";
    }

    @Override
    public void execute(AbstractList<Token> tokens) {
        InputHelper.displayInput(tokens);
        System.out.println("Выход.");
    }
}

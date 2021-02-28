package com.neevin.Commands;

import com.neevin.Parser.Token;

import java.util.AbstractList;

public class InfoCommand implements Command{
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
        System.out.println("Информация о коллекции: " + "\n" +
                "Тип коллекции: " + "\n" +
                "Дата инициализации: ");
    }
}
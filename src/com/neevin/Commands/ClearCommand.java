package com.neevin.Commands;

import com.neevin.Parser.Token;
import com.neevin.Programm.CollectionController;

import java.util.AbstractList;

public class ClearCommand implements Command {
    private CollectionController controller;

    public ClearCommand(CollectionController controller){
        this.controller = controller;
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
        controller.map.clear();
        System.out.println("Все элементы успешно удалены из коллекции.");
    }
}

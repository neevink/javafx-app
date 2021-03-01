package com.neevin.Commands;

import com.neevin.DataModels.Route;
import com.neevin.Parser.Token;
import com.neevin.Programm.CollectionController;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PrintFieldAscendingDistanceCommand implements Command{
    private CollectionController controller;

    public  PrintFieldAscendingDistanceCommand(CollectionController controller){
        this.controller = controller;
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

    }
}

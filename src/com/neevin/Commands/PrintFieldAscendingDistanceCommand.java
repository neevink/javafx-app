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
        if(controller.map.size() == 0){
            System.out.println("Коллекция пуста. Нечего выводить.");
        }

        ArrayList<Long> arr = new ArrayList<Long>();
        for(long key : controller.map.keySet()){
            long distance = controller.map.get(key).getDistance();
            arr.add(distance);
        }

        Collections.sort(arr);
        for(long e :arr){
            System.out.println(e);
        }
    }
}

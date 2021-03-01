package com.neevin.Commands;

import com.neevin.DataModels.Route;
import com.neevin.Parser.Parser;
import com.neevin.Parser.Token;
import com.neevin.Programm.CollectionController;

import java.lang.reflect.Parameter;
import java.util.AbstractList;

public class FilterGreaterThanDistanceCommand implements Command{
    private CollectionController controller;

    public  FilterGreaterThanDistanceCommand(CollectionController controller){
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "filter_greater_than_distance";
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля distance которых больше заданного\n"+
                "\t\tfilter_greater_than_distance distance";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        boolean wasPrint = false;

        long distance;
        try{
            distance = Parser.parseLong(tokens.get(1));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента distance не удался. " + e.getMessage());
        }

        for(long key : controller.map.keySet()){
            Route r = controller.map.get(key);
            if(r.getDistance() > distance){
                System.out.println(r.toString() + '\n');
                wasPrint = true;
            }
        }

        if(!wasPrint){
            System.out.println("Нет ни одного объекта, поле distance которого больше заданного.");
        }
    }
}

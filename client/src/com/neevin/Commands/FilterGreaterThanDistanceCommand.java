package com.neevin.Commands;

import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Parser;
import com.neevin.Parser.Token;
import com.neevin.Programm.Connection;

import java.util.AbstractList;

/**
 * Вывести список элементов, значение distance которое больше данного
 */
public class FilterGreaterThanDistanceCommand implements Command{
    private Connection connection;

    public  FilterGreaterThanDistanceCommand(Connection connection){
        this.connection = connection;
    }

    @Override
    public String getName() {
        return "filter_greater_than_distance";
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля distance которых больше заданного / filter_greater_than_distance distance";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        InputHelper.displayInput(tokens);

        long distance;
        try{
            distance = Parser.parseLong(tokens.get(1));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента distance не удался. " + e.getMessage());
        }

        //ExecutionService.filterGreaterThanDistance(distance);

        /*
        boolean wasPrint = false;
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
        */
    }
}
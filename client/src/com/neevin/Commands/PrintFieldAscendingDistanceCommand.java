package com.neevin.Commands;

import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Token;
import com.neevin.Programm.Connection;

import java.util.AbstractList;

/**
 * Вывести значения поля distance всех элементов в порядке возрастания
 */
public class PrintFieldAscendingDistanceCommand implements Command{
    private Connection connection;

    public  PrintFieldAscendingDistanceCommand(Connection connection){
        this.connection = connection;
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
        InputHelper.displayInput(tokens);

        //ExecutionService.printFieldAscendingDistance();
        /*
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

         */
    }
}

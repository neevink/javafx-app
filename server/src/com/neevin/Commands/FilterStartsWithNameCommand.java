package com.neevin.Commands;

import com.neevin.DataModels.Route;
import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Parser;
import com.neevin.Parser.Token;
import com.neevin.Programm.CollectionController;

import java.util.AbstractList;

/**
 * Вывести элементы, имя которых начинается со строки
 */
public class FilterStartsWithNameCommand implements Command{
    CollectionController controller;

    public FilterStartsWithNameCommand(CollectionController controller){
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "filter_starts_with_name";
    }

    @Override
    public String getDescription() {
        return " вывести элементы, значение поля name которых начинается с заданной подстроки / filter_starts_with_name \"name\"";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        InputHelper.displayInput(tokens);
        if(controller.map.size() == 0){
            System.out.println("Коллекция пуста. Нечего выводить.");
        }

        String name;
        try{
            name = Parser.parseString(tokens.get(1));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента name не удался. " + e.getMessage());
        }

        boolean wasPrint = false;
        for(long key : controller.map.keySet()){
            Route r = controller.map.get(key);

            if(r.getName().startsWith(name)){
                System.out.println(r.toString() + '\n');
                wasPrint = true;
            }
        }

        if(!wasPrint){
            System.out.println("Нет ни одного объекта, поле distance которого больше заданного.");
        }
    }
}

package com.neevin.Commands;

import com.neevin.DataModels.Route;
import com.neevin.Parser.Parser;
import com.neevin.Parser.Token;
import com.neevin.Programm.CollectionController;

import java.util.AbstractList;
import java.util.ArrayList;

public class RemoveLowerKeyCommand implements Command{
    private CollectionController controller;

    public  RemoveLowerKeyCommand(CollectionController controller){
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "remove_lower_key";
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, ключ которых меньше, чем заданный"+
                "\t\tremove_lower_key id";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        long id;
        try{
            id = Parser.parseLong(tokens.get(1));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента distance не удался. " + e.getMessage());
        }

        ArrayList<Long> keys = new ArrayList<Long>();
        for(long key : controller.map.keySet()){
            keys.add(key);
        }

        int count = 0;
        for(long key : keys){
            Route r = controller.map.get(key);
            if(r.getId() < id){
                controller.map.remove(key);
                count++;
            }
        }

        System.out.println(String.format("Из коллекции успешно удалено %d элементов.", count));
    }
}

package com.neevin.Commands;

import com.neevin.Parser.Token;
import com.neevin.Programm.CollectionController;
import com.neevin.Programm.SaveManager;

import java.io.FileNotFoundException;
import java.util.AbstractList;
import java.util.Date;

public class SaveCommand implements Command{
    private SaveManager saveManager;
    private CollectionController controller;

    public SaveCommand(SaveManager saveManager, CollectionController controller){
        this.saveManager = saveManager;
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }

    // Это говно переписать
    @Override
    public void execute(AbstractList<Token> tokens) {
        try {
            saveManager.Save(controller.dictionary, new Date(0));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

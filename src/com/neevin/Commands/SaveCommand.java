package com.neevin.Commands;

import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Token;
import com.neevin.Programm.CollectionController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractList;

/**
 * Сохранить состояние коллекции в фвйле
 */
public class SaveCommand implements Command{
    private CollectionController controller;

    public SaveCommand(CollectionController controller){
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
        InputHelper.displayInput(tokens);

        try {
            controller.Save();

            System.out.println("Текущее состояние коллекции успешно сохранено в файле.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

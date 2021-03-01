package com.neevin;

/*
Лабораторная 5
Вариант: 313087


 */

import com.neevin.Programm.CollectionController;
import com.neevin.Programm.CommandManager;
import com.neevin.Programm.Programm;

public class Main {

    final static String environmentVariable = "PROG_LAB_FILE_PATH";

    public static void main(String[] args) {
        CollectionController col;
        try {
            col = new CollectionController(environmentVariable);
        }
        catch (Exception e){
            System.out.println(e);
            return;
        }

        CommandManager cm = new CommandManager(col);
        Programm.run(cm);
    }
}

package com.neevin;

/*
Лабораторная 5
Вариант: 313087


 */

import com.neevin.Programm.CollectionController;
import com.neevin.Programm.CommandManager;
import com.neevin.Programm.Programm;

import java.util.Scanner;

public class Main {
    final static String ENVIRONMENT_VARIABLE = "PROG_LAB_FILE_PATH";

    public static void main(String[] args) {
        CollectionController col;
        try {
            col = new CollectionController(ENVIRONMENT_VARIABLE);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        CommandManager cm = new CommandManager(col, new Scanner(System.in));
        Programm.run(cm, new Scanner(System.in));
    }
}

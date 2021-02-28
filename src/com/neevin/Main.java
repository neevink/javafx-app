package com.neevin;


/*
Лабораторная 5
Вариант: 313087


 */

import com.neevin.Programm.CollectionController;
import com.neevin.Programm.CommandManager;
import com.neevin.Programm.SaveManager;

import java.util.Scanner;

public class Main {

    final static String environmentVariable = "PROG_LAB_FILE_PATH";

    public static void main(String[] args) {
        CommandManager cc = new CommandManager(new CollectionController(), new SaveManager(environmentVariable));

        Scanner sc = new Scanner(System.in);
        String input;

        // Читаем новые строки и парсим их до тех пор, пока очередная не будет "exit"
        do{
            input = sc.nextLine();
            try {
                cc.ParseAndExecute(input);
            }
            catch (Exception e){
                System.out.println("Произошла ошибка: " + e.getMessage());
            }

        }while ( !input.equals("exit") );
    }
}

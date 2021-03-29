package com.neevin.Programm;

import java.util.Scanner;

/**
 * Чтение команд и передача на парсинг и исполнение
 */
public class Programm {
    public static void run(CommandManager cm, Scanner scanner){
        String input;

        // Читаем новые строки и парсим их до тех пор, пока очередная не будет "exit"
        do{
            if(!scanner.hasNextLine()){
                return;
            }
            input = scanner.nextLine();
            try {
                cm.ParseAndExecute(input);
            }
            catch (Exception e){
                System.out.println("Произошла ошибка: " + e.getMessage());
            }
            System.out.println();

        }while ( !input.equals("exit") );
    }
}

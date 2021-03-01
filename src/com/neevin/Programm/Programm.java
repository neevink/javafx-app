package com.neevin.Programm;

import java.io.InputStream;
import java.util.Scanner;

public class Programm {
    public static void run(CommandManager cm){
        Scanner sc = new Scanner(System.in);
        String input;

        // Читаем новые строки и парсим их до тех пор, пока очередная не будет "exit"
        do{
            input = sc.nextLine();
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

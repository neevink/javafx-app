package com.neevin;

/*
Лабораторная 5
Вариант: 313087

Лабораторная 6
Варинат: 313387

 */


import com.neevin.Programm.Connection;
import com.neevin.Programm.CommandManager;
import com.neevin.Programm.Programm;

import java.util.Scanner;

public class ClientMain {
    // Поскольку ip это localhost, а вот порт меняется, тогда может понадобиться возможность сменить порт
    private static int port = 12345;

    public static void main(String[] args) {
        // Если предан другой порт в командной строке
        if(args.length == 1){
            try{
                port = Integer.parseInt(args[0]);
            }
            catch (Exception e){
                System.out.println("Что-то не получилось спарсить порт из агрументов командной строки, используется стандартный");
            }
        }

        Connection connection = new Connection(port);
        CommandManager cm = new CommandManager(connection, new Scanner(System.in));
        Programm.run(cm, new Scanner(System.in));
    }
}

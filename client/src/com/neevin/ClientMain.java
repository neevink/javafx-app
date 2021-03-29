package com.neevin;

import com.neevin.Programm.RequestSender;
import com.neevin.Programm.CommandManager;
import com.neevin.Programm.Programm;
import java.util.Scanner;

/*
Лабораторная 5
Вариант: 313087

Лабораторная 6
Варинат: 313387

 */

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

        RequestSender requestSender = new RequestSender(port);
        CommandManager cm = new CommandManager(requestSender, new Scanner(System.in));
        System.out.println("Клиентское приложение запущено!");
        Programm.run(cm, new Scanner(System.in));
    }
}

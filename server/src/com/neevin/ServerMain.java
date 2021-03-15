package com.neevin;

/*
Лабораторная 5
Вариант: 313087

Лабораторная 6
Варинат: 313387

 */

import com.neevin.Programm.CollectionController;
import com.neevin.Programm.CommandManager;
import com.neevin.Programm.Programm;

import java.io.ByteArrayInputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ServerMain {
    // Поскольку ip это localhost, а вот порт меняется, тогда может понадобиться возможность сменить порт
    private static int port = 12345;
    final static String ENVIRONMENT_VARIABLE = "PROG_LAB_FILE_PATH";

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

        try{
            byte[] b = new byte[3];


            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(port));

            // Тут ждём
            SocketChannel s = ssc.accept();


            CollectionController col;
            try {
                col = new CollectionController(ENVIRONMENT_VARIABLE);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                return;
            }

            CommandManager cm = new CommandManager(col, new Scanner(s));
            Programm.run(cm, new Scanner(s));


        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}

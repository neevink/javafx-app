package com.neevin;

/*
Лабораторная 5
Вариант: 313087

Лабораторная 6
Варинат: 313387

 */

import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;
import com.neevin.Programm.CollectionController;
import com.neevin.Programm.ExecutionService;

import java.io.*;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

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

        CollectionController collectionController;
        try {
            collectionController = new CollectionController(ENVIRONMENT_VARIABLE);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }


        ServerSocketChannel serverChannel;
        try{
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(port));
        }
        catch (IOException exc){
            System.out.println("Ошибка запуска сервера! Скорее всего занят порт!");
            return;
        }

        ExecutionService executionService = new ExecutionService(collectionController);
        while (true) {
            try(SocketChannel clientChannel = serverChannel.accept()){
                System.out.println("Полступил новый запрос с машины " + clientChannel.getRemoteAddress());

                ObjectInputStream ios = new ObjectInputStream(clientChannel.socket().getInputStream());
                Request<?> request = (Request<?>) ios.readObject();
                System.out.println("Запрос : " + request.command);

                CommandResult result = executionService.executeCommand(request);
                if(result.status == ResultStatus.OK){
                    System.out.println("Результат выполнения: успешно");
                }
                else{
                    System.out.println("Результат выполнения: неуспешно");
                }
                System.out.println();

                OutputStream os = clientChannel.socket().getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(result);
            }
            catch (IOException | ClassNotFoundException exc){
                System.out.println(exc.getMessage());
            }
        }
    }
}

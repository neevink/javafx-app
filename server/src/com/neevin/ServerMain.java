package com.neevin;

/*
Лабораторная 5
Вариант: 313087

Лабораторная 6
Варинат: 313387

Лабораторная 7
Варинат: 313589

 */

import com.neevin.Database.DatabaseConnection;
import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;
import com.neevin.Programm.ExecutionService;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerMain {
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

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Отстутствует драйвер базы данных PostgreSQL!");
        }

        ServerSocketChannel serverChannel;
        try{
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            System.out.println("Сервер успешно запущен. Порт: " + port);
        }
        catch (IOException exc){
            System.out.println("Ошибка запуска сервера! Скорее всего занят порт!");
            return;
        }

        DatabaseConnection db;
        ExecutionService executionService;
        try{
            db = new DatabaseConnection(System.getProperty("user.dir") + "\\database-connection.yaml");
            System.out.println("Файл с данными для подключения к БД найден и подключение успешно установлено.");

            executionService = new ExecutionService(db);
        }
        catch (Exception exc){
            System.out.println(exc.getMessage());
            return;
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Выход");
            }
        });

        AtomicBoolean exit = new AtomicBoolean(false);
        Thread thread = getUserInputHandler(exit);
        thread.start();

        while (!exit.get()) {
            try(SocketChannel clientChannel = serverChannel.accept()){
                if (clientChannel == null){
                    continue;
                }
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

    private static Thread getUserInputHandler(AtomicBoolean exit){
        return new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            while (true){
                if(scanner.hasNextLine()){
                    String serverCommand = scanner.nextLine();
                    System.out.println("Введено: " + serverCommand);

                    switch (serverCommand){
                        case "exit":
                            exit.set(true);
                            return;
                        default:
                            System.out.println("Такой команды не существует");
                            break;
                    }
                }
                else{
                    exit.set(true);
                    return;
                }
            }
        });
    }
}

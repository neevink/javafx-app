package com.neevin.Programm;

import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class RequestReaderRunnable implements Runnable {
    SocketChannel client;
    ExecutionService executionService;
    private static final int THREAD_COUNT = 5;
    private static ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);

    public RequestReaderRunnable(SocketChannel client, ExecutionService executionService){
        this.client = client;
        this.executionService = executionService;
    }

    @Override
    public void run() {
        try {
            System.out.println("Полступил новый запрос с машины " + client.getRemoteAddress());
            ObjectInputStream ois = new ObjectInputStream(client.socket().getInputStream());
            Request<?> request = (Request<?>) ois.readObject();
            System.out.println("Запрос : " + request.command);

            // Выполнение при помощи FixedThreadPool
            FutureTask ft = new FutureTask(new RequestHandler(executionService, request));
            pool.submit(ft);
            CommandResult result = (CommandResult) ft.get();

            // Отправка ответа при помощи создания нового потока
            Thread t = new Thread(() -> {
                OutputStream os = null;
                try {
                    os = client.socket().getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            t.run();
            client.close();
        }
        catch (Exception exc){
            exc.printStackTrace();
            System.out.println(exc.getMessage());
        }
    }
}

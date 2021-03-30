package com.neevin.Programm;

import com.neevin.Net.CommandResult;
import com.neevin.Net.ResultStatus;
import com.neevin.Net.Request;
import java.io.*;
import java.net.Socket;

/**
 * Класс, который позваляет оправлять запросы на сервер по сети
 */
public class RequestSender {
    protected int port;
    protected final int MAX_ATTEMPTS_COUNT = 5;

    public RequestSender(int port){
        this.port = port;
    }

    /**
     * Отправить запрос на выполнение
     * @param request Запрос
     * @return Результат выполнения
     */
    public CommandResult sendRequest(Request<?> request){
        if(request == null){
            throw new IllegalArgumentException("Запрос не может быть null!");
        }

        int attempts = 0; // Сколько было попыток отправить запрос
        while (attempts < MAX_ATTEMPTS_COUNT){
            try{
                Socket socket = new Socket("127.0.0.1", port);

                OutputStream os = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(request);

                InputStream is = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                CommandResult result = (CommandResult) ois.readObject();
                if(attempts != 0){
                    System.out.println("Вау! Появилось подключение!");
                }
                attempts = MAX_ATTEMPTS_COUNT;
                return result;
            }
            catch (IOException | ClassNotFoundException exc){
                System.out.println("Не удалось подключиться к серверу, но мы подождём, мало ли...");
                attempts++;
                try {
                    Thread.sleep(5 * 1000);
                }
                catch (Exception e) { }
            }
        }
        return new CommandResult(ResultStatus.ERROR, "Не удалось подключиться к серверу, больше пытаться нет смысла.");
    }
}

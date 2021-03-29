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

        try{
            Socket socket = new Socket("127.0.0.1", port);

            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(request);

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            CommandResult result = (CommandResult) ois.readObject();
            return result;
        }
        catch (IOException | ClassNotFoundException exc){
            return new CommandResult(ResultStatus.OK, "Не удалось подключиться к серверу, выполнение команды отменено.");
        }
    }
}

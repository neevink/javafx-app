package com.neevin.Programm;

import com.neevin.DataModels.Account;
import com.neevin.Net.CommandResult;
import com.neevin.Net.ResultStatus;
import com.neevin.Net.Request;
import java.io.*;
import java.net.Socket;

/**
 * Класс, который позваляет оправлять запросы на сервер по сети, хранит логин и пароль для авторизации
 */
public class RequestSender {
    protected int port;
    protected final int MAX_ATTEMPTS_COUNT = 5;

    protected String userLogin;
    protected String userPassword;

    public RequestSender(int port){
        this.port = port;
    }

    public boolean authorizeUser(String userLogin, String userPassword){
        //Request<Account> request = new Request<>("login");


        return false;
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
        }

        return new CommandResult(ResultStatus.ERROR, "Не удалось подключиться к серверу.");
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}

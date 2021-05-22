package com.neevin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

import com.neevin.DataModels.Account;
import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;
import com.neevin.Programm.RequestSender;
import com.neevin.Programm.CommandManager;
import com.neevin.Programm.Programm;
import java.io.Console;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ClientMain extends Application{
    // Поскольку ip это localhost, а вот порт меняется, тогда может понадобиться возможность сменить порт
    private static int port = 12345;
    public static final RequestSender requestSender = new RequestSender(port);
    public static ResourceBundle resources = ResourceBundle.getBundle("resources.resource", new Locale("ru"));
    public static String selectedLanguage = "ru";

    public static void changeLanguage(String lang){
        resources = ResourceBundle.getBundle("resources.resource", new Locale(lang));
        selectedLanguage = lang;
    }

    public static void main(String[] args) {
        Application.launch(args);

        // Будет перерисано
        Scanner scanner = new Scanner(System.in);
        CommandManager cm = new CommandManager(requestSender, scanner);


        System.out.println("Клиентское приложение запущено!");

        Programm.run(cm, new Scanner(System.in));
    }

    private static void handleLogin(Scanner scanner, RequestSender requestSender, Console console) {
        String password = "";
        String login = "";
        boolean successAuth = false;
        while(!successAuth){
            if (true) {
                System.out.println("Имя аккаунта: ");
                if(scanner.hasNextLine()){
                    login = scanner.nextLine();
                }
                else {
                    System.exit(-1);
                }

                if(login.length() < 3){
                    System.out.println("Короткое имя пользователя!");
                    continue;
                }

                System.out.println("Пароль: ");
                char passwd[] = console.readPassword("Придумай пароль: ");
                if (passwd == null) {
                    System.exit(1);
                }
                password = String.valueOf(passwd);
            }

            Account account = new Account(login, password);
            Request<Account> request = new Request<Account>("login", account, null, null);
            CommandResult result = requestSender.sendRequest(request);

            if(result.status == ResultStatus.OK){
                requestSender.setUserLogin(login);
                requestSender.setUserPassword(password);
                successAuth = true;
                System.out.println(result.message);
            }
            else{
                System.out.println("Произошла ошибка: " + result.message);
            }
        }
    }

    private static void handleRegister(Scanner scanner, RequestSender requestSender, Console console) {
        final int MIN_PASSWORD_LENGTH = 6;

        String password = "";
        String login = "";
        String passwordAgain = "";
        boolean successRegister = false;
        while(!successRegister){
            if (console != null) {
                System.out.println("Имя аккаунта: ");
                if(scanner.hasNextLine()){
                    login = scanner.nextLine();
                }
                else {
                    System.exit(-1);
                }

                if(login.length() < 3){
                    System.out.println("Короткое имя пользователя!");
                    continue;
                }

                // Логин может состоять только из букв, цифр и символа нижнего подчёркивания
                if(!login.chars().allMatch(x -> (x == '_' || Character.isLetterOrDigit(x)))){
                    System.out.println("Имя аккаунта может состоять только из букв, цифр и символа нижнего подчёркивания");
                    continue;
                }

                char passwd[] = console.readPassword("Придумай пароль: ");
                if (passwd == null) {
                    System.exit(1);
                }
                password = String.valueOf(passwd);

                if(password.length() < MIN_PASSWORD_LENGTH){
                    System.out.println("Пароль должен состоять минимум из " + MIN_PASSWORD_LENGTH + " символов");
                    continue;
                }

                passwd = console.readPassword("Повтори пароль: ");
                if (passwd == null) {
                    System.exit(1);
                }
                passwordAgain = String.valueOf(passwd);

                if(!password.equals(passwordAgain)){
                    System.out.println("Пароли не сопадают!");
                    continue;
                }
            }

            Account account = new Account(login, password);
            Request<Account> request = new Request<Account>("register", account, null, null);
            CommandResult result = requestSender.sendRequest(request);

            if(result.status == ResultStatus.OK){
                requestSender.setUserLogin(login);
                requestSender.setUserPassword(password);
                successRegister = true;
                System.out.println(result.message);
            }
            else{
                System.out.println("Произошла ошибка: " + result.message);
            }
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("./Views/LoginOrRegisterView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setMinWidth(600);
        stage.setMinHeight(400);

        stage.setTitle("Управление маршрутами");
        stage.show();
    }
}

package com.neevin;

import com.neevin.DataModels.Account;
import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;
import com.neevin.Programm.RequestSender;
import com.neevin.Programm.CommandManager;
import com.neevin.Programm.Programm;

import java.io.Console;
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

        Scanner scanner = new Scanner(System.in);
        RequestSender requestSender = new RequestSender(port);
        CommandManager cm = new CommandManager(requestSender, scanner);
        System.out.println("Клиентское приложение запущено!");

        Console console = System.console();
        if (console == null) {
            System.out.println("Консоли нет! Юзаем сканер! Поэтому будет виден пароль!");
        }

        System.out.println("У вас есть аккаунт? (да/нет)");
        if(!scanner.hasNextLine()){
            return;
        }

        String ans = scanner.nextLine().trim();
        if(ans.equals("да")){
            handleLogin(scanner, requestSender, console);
        }
        else {
            handleRegister(scanner, requestSender, console);
        }

        Programm.run(cm, new Scanner(System.in));
    }

    private static void handleLogin(Scanner scanner, RequestSender requestSender, Console console) {
        String password;
        String login;
        boolean successAuth = false;
        while(!successAuth){
            if (console != null) {
                char[] loginArr = console.readPassword("Имя аккаунта: ");
                login = String.valueOf(loginArr);
                char[] pwdArr = console.readPassword("Пароль: ");
                password = String.valueOf(pwdArr);
            }
            else{
                System.out.print("Имя аккаунта: ");
                if(!scanner.hasNextLine()){
                    return;
                }
                login = scanner.nextLine();
                System.out.print("Пароль: ");
                if(!scanner.hasNextLine()){
                    return;
                }
                password = scanner.nextLine();
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

        String password;
        String login;
        boolean successRegister = false;
        while(!successRegister){
            if (console != null) {
                char[] loginArr = console.readPassword("Введите имя нового аккаунта: ");
                login = String.valueOf(loginArr);

                // Логин может состоять только из букв, цифр и символа нижнего подчёркивания
                if(!login.chars().allMatch(x -> (x == '_' || Character.isLetterOrDigit(x)))){
                    System.out.println("Имя аккаунта может состоять только из букв, цифр и символа нижнего подчёркивания");
                    continue;
                }

                char[] pwdArr = console.readPassword("Придумайте пароль: ");
                password = String.valueOf(pwdArr);
                if(password.length() < MIN_PASSWORD_LENGTH){
                    System.out.println("Пароль должен состоять минимум из " + MIN_PASSWORD_LENGTH + " символов");
                    continue;
                }

                pwdArr = console.readPassword("Повторите пароль: ");
                String passwordAgain = String.valueOf(pwdArr);

                if(!password.equals(passwordAgain)){
                    System.out.println("Пароли не сопадают!");
                    continue;
                }
            }
            else{
                System.out.print("Введите имя нового аккаунта: ");
                if(!scanner.hasNextLine()){
                    return;
                }
                login = scanner.nextLine();

                // Логин может состоять только из букв, цифр и символа нижнего подчёркивания
                if(!login.chars().allMatch(x -> (x == '_' || Character.isLetterOrDigit(x)))){
                    System.out.println("Имя аккаунта может состоять только из букв, цифр и символа нижнего подчёркивания");
                    continue;
                }

                System.out.print("Придумайте пароль: ");
                if(!scanner.hasNextLine()){
                    return;
                }
                password = scanner.nextLine();
                if(password.length() < MIN_PASSWORD_LENGTH){
                    System.out.println("Пароль должен состоять минимум из " + MIN_PASSWORD_LENGTH + " символов");
                    continue;
                }

                System.out.print("Повторите пароль: ");
                if(!scanner.hasNextLine()){
                    return;
                }
                String passwordAgain = scanner.nextLine();

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
}

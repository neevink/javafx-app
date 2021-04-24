package com.neevin.Commands;

import com.neevin.DataModels.Route;
import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;
import com.neevin.Parser.InputHelper;
import com.neevin.Parser.Parser;
import com.neevin.Parser.Token;
import com.neevin.Programm.RequestSender;

import java.io.Console;
import java.util.AbstractList;
import java.util.Scanner;

// Выпили потом
public class LogInCommand implements Command{
    RequestSender requestSender;
    Scanner input;

    public LogInCommand(RequestSender requestSender, Scanner scanner){
        this.requestSender = requestSender;
        this.input = scanner;
    }

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public String getDescription() {
        return "войти в аккаунт / login \"account_name\"";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        InputHelper.displayInput(tokens);

        if(tokens == null){
            throw new IllegalArgumentException("Сисок токенов не может быть равен null!");
        }

        // Токенов должно быть 1: имя аккауента
        if(tokens.size() != 2){
            throw new IllegalArgumentException("Аргументов этой команды должно быть 1.");
        }

        String account_name;
        try{
            account_name = Parser.parseString(tokens.get(1));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента account_name не удался. " + e.getMessage());
        }

        Console console = System.console();
        String password;
        if (console != null) {
            char[] arr = console.readPassword("Password: ");
            password = String.valueOf(arr);
        }
        else{
            System.out.println("Консоли нет! Юзаем сканер!");
            password = input.nextLine();
        }

        /*
        Request<?> request = new Request<Object>(this.getName(), new Object());
        CommandResult result = requestSender.sendRequest(request);

        if(result.status == ResultStatus.OK){
            System.out.println(result.message);
        }
        else{
            System.out.println("Произошла ошибка: " + result.message);
        }
        */
    }
}

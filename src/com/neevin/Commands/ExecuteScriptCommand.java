package com.neevin.Commands;

import com.neevin.Parser.Parser;
import com.neevin.Parser.Token;
import com.neevin.Programm.CollectionController;
import com.neevin.Programm.CommandManager;
import com.neevin.Programm.Programm;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Scanner;

public class ExecuteScriptCommand implements Command{
    CollectionController controller;
    CommandManager commandManager;

    public ExecuteScriptCommand(CommandManager commandManager,CollectionController controller){
        this.commandManager = commandManager;
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме / execute_script \"file_name\"";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        System.out.println("Тэта тэ скриптик выполняется");

        String path;
        try{
            path = Parser.parseString(tokens.get(1));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента file_name не удался. " + e.getMessage());
        }

        File script = new File(path);

        if(!script.exists()){
            throw new Exception("Файла со скриптом не существует!");
        }
        if(!script.canRead()){
            throw new Exception("Нет прав на чтение файла со скриптом!");
        }

        FileReader reader = new FileReader(script);

        StringBuffer stringBuffer = new StringBuffer();

        int c;
        c = reader.read();
        while(c != -1){
            stringBuffer.append((char) c);
            c = reader.read();
        }

        String s = stringBuffer.toString();
        InputStream stream = new StringBufferInputStream(stringBuffer.toString());

        Programm p = new Programm();
        CommandManager cm = new CommandManager(controller, stream);
        p.run(cm, stream);
    }
}

package com.neevin.Commands;

import com.neevin.Parser.Token;
import com.neevin.Programm.CollectionController;

import java.util.AbstractList;

public class ExecuteScriptCommand implements Command{
    CollectionController controller;

    public ExecuteScriptCommand(CollectionController controller){
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n"+
                "\t\texecute_script file_name";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        System.out.println("Тэта тэ скриптик выполняется");
    }
}

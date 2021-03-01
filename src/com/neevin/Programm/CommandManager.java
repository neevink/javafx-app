package com.neevin.Programm;

import com.neevin.Commands.*;
import com.neevin.Parser.Token;
import com.neevin.Parser.TokenType;
import com.neevin.Parser.Tokenizer;

import java.io.InputStream;
import java.util.*;

public class CommandManager {
    /**
     * Все доступные в программе команды
     */
    protected HashMap<String,Command> commands = new HashMap<String, Command>();

    public CommandManager(CollectionController controller){
        registerCommand(new HelpCommand(this));
        registerCommand(new InfoCommand(controller));
        registerCommand(new ShowCommand(controller));
        registerCommand(new InsertCommand(controller));
        registerCommand(new UpdateCommand(controller));
        registerCommand(new RemoveCommand(controller));
        registerCommand(new ClearCommand(controller));
        registerCommand(new SaveCommand(controller));
        registerCommand(new ExecuteScriptCommand(this, controller));
        registerCommand(new ExitCommand());
        registerCommand(new ReplaceIfGreaterCommand(controller));
        registerCommand(new ReplaceIfLoweCommand(controller));
        registerCommand(new RemoveLowerKeyCommand(controller));
        registerCommand(new FilterStartsWithNameCommand(controller));
        registerCommand(new FilterGreaterThanDistanceCommand(controller));
        registerCommand(new PrintFieldAscendingDistanceCommand(controller));
    }

    public void ParseAndExecute(String input) throws Exception {
        ArrayList<Token> tokens;
        try{
            tokens = Tokenizer.tokenize(input);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        if(tokens.size() == 0){
            System.out.println("Введённая строка пуста!");
        }

        Token firstToken = tokens.get(0);

        if(firstToken.type != TokenType.WORD){
            throw new Exception("В строке первым должно быть написано название команды, потом её аргументы через пробел.");
        }

        if(!commands.containsKey(firstToken.object)){
            throw new IndexOutOfBoundsException(String.format("Команды \"%s\" не существует!", firstToken.object));
        }

        try{
            commands.get(firstToken.object).execute(tokens);
        }
        catch (Exception e){
            System.out.println("Произошла ошибка: " + e.getMessage());
            System.out.println("Выполнение команды отменено.");
        }
    }

    void registerCommand(Command newCommand){
        if(commands.containsKey(newCommand.getName())){
            throw new IllegalArgumentException("Команда с таким именем уже существует!");
        }

        commands.put(newCommand.getName(), newCommand);
    }

    public String getCommandsNameWithDescription(){
        String result = "";

        for (Map.Entry<String, Command> e : commands.entrySet()) {
            Command command = e.getValue();
            result +=  command.getName() + " - " + command.getDescription() + "\n";
        }
        return result;
    }
}

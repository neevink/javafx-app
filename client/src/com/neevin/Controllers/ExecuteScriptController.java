package com.neevin.Controllers;

import com.neevin.ClientMain;
import com.neevin.Commands.ExecuteScriptCommand;
import com.neevin.Programm.CommandManager;
import com.neevin.Programm.Programm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.*;

public class ExecuteScriptController extends BaseController{
    final FileChooser fileChooser = new FileChooser();

    @FXML
    private Button backButton;
    @FXML
    private Button chooseFileButton;

    @FXML
    public Label outputLabel;

    @Override
    public void initialize() {
        super.initialize();
    }

    @FXML
    private void backButtonClick(javafx.event.ActionEvent event) throws IOException {
        changeView(event, "/com/neevin/Views/MainView.fxml");
    }

    @FXML
    private void chooseFileButtonClick(ActionEvent event) throws FileNotFoundException {
        StringBuilder output = new StringBuilder();

        File script = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        if(script == null){
            output.append("Не удалось получить доступ к файлу, возможно указана директория вместо файла!\n");
            return;
        }

        if(!script.exists()){
            output.append("Файла со скриптом не существует!\n");
            return;
        }
        if(!script.canRead()){
            output.append("Нет прав на чтение файла со скриптом!\n");
            return;
        }
        if(ExecuteScriptCommand.executingScripts.contains(script.getAbsolutePath())){
            output.append("Этот скрипт уже выполняется, в целях избежания рекурсии его выполнение запрещено.\n");
        }

        Scanner fileScanner = new Scanner(new BufferedInputStream(new FileInputStream(script)));

        output.append("Началось выполнение скрипта из файла " + script.getAbsolutePath() + "\n");
        ExecuteScriptCommand.executingScripts.add(script.getAbsolutePath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        CommandManager cm = new CommandManager(ClientMain.requestSender, fileScanner);
        Programm.run(cm, fileScanner);

        output.append(baos.toString());

        ExecuteScriptCommand.executingScripts.remove(script.getAbsolutePath());
        output.append("Выполение скрипта из файла " + script.getAbsolutePath() + " завершено\n");

        System.out.flush();
        System.setOut(old);

        outputLabel.setText(output.toString());
        fileScanner.close();
    }

    @Override
    public void updateLanguage() {
        super.updateLanguage();
        backButton.setText(ClientMain.resources.getString("Back"));
        chooseFileButton.setText(ClientMain.resources.getString("ChooseFile"));
    }
}

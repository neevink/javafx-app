package com.neevin;

import com.neevin.Controllers.BaseController;
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
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("./Views/LoginOrRegisterView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            BaseController.resized();
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            BaseController.resized();
        });

        stage.setMinWidth(600);
        stage.setMinHeight(400);

        stage.setTitle("Управление маршрутами");
        stage.show();
    }
}

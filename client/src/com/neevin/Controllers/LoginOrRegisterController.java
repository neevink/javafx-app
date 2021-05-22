package com.neevin.Controllers;

import com.neevin.ClientMain;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.Console;
import java.io.IOException;

public class LoginOrRegisterController extends BaseController {
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;


    @FXML
    private void openRegisterClick(ActionEvent event) throws IOException {
        changeView(event, "/com/neevin/Views/RegisterView.fxml");
    }

    @FXML
    private void openLoginClick(ActionEvent event) throws IOException {
        changeView(event, "/com/neevin/Views/LoginView.fxml");
    }

    @Override
    public void updateLanguage() {
        super.updateLanguage();
        loginButton.setText(ClientMain.resources.getString("Login"));
        registerButton.setText(ClientMain.resources.getString("Register"));
    }
}
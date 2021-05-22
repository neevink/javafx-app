package com.neevin.Controllers;

import com.neevin.ClientMain;
import com.neevin.DataModels.Account;
import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController extends BaseController{
    @FXML
    Button cancelButton;
    @FXML
    Button registerButton;
    @FXML
    Label loginAccountNameLabel;
    @FXML
    Label passwordLabel;
    @FXML
    Label passwordAgainLabel;

    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField passwordAgainField;

    @FXML
    private void createAccount(ActionEvent event) throws IOException {
        final int MIN_LOGIN_LENGTH = 3;
        final int MIN_PASSWORD_LENGTH = 6;
        String login = loginField.getText();
        String password = passwordField.getText();
        String passwordAgain = passwordAgainField.getText();

        if(login.length() < MIN_LOGIN_LENGTH){
            createAlert("Слишком короткое имя пользователя!");
            return;
        }

        if(!login.chars().allMatch(x -> (x == '_' || Character.isLetterOrDigit(x)))){
            createAlert("Имя аккаунта может состоять только из букв, цифр и символа нижнего подчёркивания");
            return;
        }

        if(password.length() < MIN_PASSWORD_LENGTH){
            createAlert("Пароль должен состоять минимум из " + MIN_PASSWORD_LENGTH + " символов");
            return;
        }

        if(!password.equals(passwordAgain)){
            createAlert("Пароли не совпадают!");
            return;
        }

        Account account = new Account(login, password);
        Request<Account> request = new Request<Account>("register", account, null, null);
        CommandResult result = ClientMain.requestSender.sendRequest(request);

        if(result.status == ResultStatus.OK){
            ClientMain.requestSender.setUserLogin(login);
            ClientMain.requestSender.setUserPassword(password);

            changeView(event, "/com/neevin/Views/MainView.fxml");
        }
        else{
            createAlert("Произошла ошибка: " + result.message);
        }
    }

    @FXML
    private void backClick(ActionEvent event) throws IOException {
        changeView(event, "/com/neevin/Views/LoginOrRegisterView.fxml");
    }

    @Override
    public void updateLanguage() {
        super.updateLanguage();
        loginAccountNameLabel.setText(ClientMain.resources.getString("AccountName"));
        passwordLabel.setText(ClientMain.resources.getString("Password"));
        passwordAgainLabel.setText(ClientMain.resources.getString("PasswordAgain"));
        cancelButton.setText(ClientMain.resources.getString("Cancel"));
        registerButton.setText(ClientMain.resources.getString("Register"));
    }
}

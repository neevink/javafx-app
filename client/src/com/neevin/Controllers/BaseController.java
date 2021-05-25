package com.neevin.Controllers;

import com.neevin.ClientMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class BaseController {
    @FXML
    private ChoiceBox languageChoiceBox;
    @FXML
    protected Label accountNameLabel;
    @FXML
    protected Button logoutButton;

    public static void resized(){ }

    public void initialize(){
        updateLanguage();
        if(ClientMain.selectedLanguage.equals("ru")){
            languageChoiceBox.setValue("Русский");
        }
        else if(ClientMain.selectedLanguage.equals("tr")){
            languageChoiceBox.setValue("Türk");
        }
        else if(ClientMain.selectedLanguage.equals("da")){
            languageChoiceBox.setValue("Dansk");
        }
        else if(ClientMain.selectedLanguage.equals("es")){
            languageChoiceBox.setValue("Español");
        }

        if(accountNameLabel != null){
            // Установить имя текущего пользователя
            accountNameLabel.setText(ClientMain.requestSender.getUserLogin());
        }
    }

    // Обновить названия всех элементов на экране
    public void updateLanguage(){
        if(logoutButton != null){
            logoutButton.setText(ClientMain.resources.getString("Exit"));
        }
    }

    public void changeLanguage(ActionEvent event){
        String newLanguage = ((ChoiceBox)event.getSource()).getValue().toString();
        if(newLanguage.equals("Русский")){
            ClientMain.changeLanguage("ru");
        }
        else if(newLanguage.equals("Türk")){
            ClientMain.changeLanguage("tr");
        }
        else if(newLanguage.equals("Dansk")){
            ClientMain.changeLanguage("da");
        }
        else{ // Испанский
            ClientMain.changeLanguage("es");
        }
        updateLanguage();
    }

    @FXML
    protected void logout(ActionEvent event) throws IOException {
        ClientMain.requestSender.setUserLogin(null);
        ClientMain.requestSender.setUserPassword(null);
        changeView(event, "/com/neevin/Views/LoginOrRegisterView.fxml");
    }

    protected void changeView(ActionEvent event, String viewPath) throws IOException {
        Parent blah = FXMLLoader.load(getClass().getResource(viewPath));
        Scene scene = new Scene(blah);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    protected void changeView(Parent element, String viewPath) throws IOException {
        Parent blah = FXMLLoader.load(getClass().getResource(viewPath));
        Scene scene = new Scene(blah);
        Stage appStage = (Stage) element.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    protected void createAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");
        alert.setContentText("Произошла ошибка: " + message);
        alert.showAndWait();
    }
}

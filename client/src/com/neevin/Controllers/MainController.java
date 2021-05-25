package com.neevin.Controllers;

import com.neevin.ClientMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;

public class MainController extends BaseController{
    @FXML
    protected Button visualizationButton;
    @FXML
    protected Button showButton;
    @FXML
    protected Button executeScriptButton;
    @FXML
    protected Button infoButton;
    @FXML
    protected Button replaceIfButton;

    public void visualizationButtonClick(ActionEvent event) throws IOException {
        changeView(event, "/com/neevin/Views/VisualisationView.fxml");
    }

    public void showButtonClick(ActionEvent event) throws IOException {
        changeView(event, "/com/neevin/Views/ShowView.fxml");
    }

    public void executeScriptButtonClick(ActionEvent event) throws IOException {
        changeView(event, "/com/neevin/Views/ExecuteScriptView.fxml");
    }

    public void infoButtonClick(ActionEvent event) throws IOException {
        changeView(event, "/com/neevin/Views/InformationView.fxml");
    }

    public void replaceIfButtonClick(ActionEvent event) throws IOException {
        changeView(event, "/com/neevin/Views/ReplaceIfView.fxml");
    }

    @Override
    public void updateLanguage() {
        super.updateLanguage();
        visualizationButton.setText(ClientMain.resources.getString("VisualizationArea"));
        showButton.setText(ClientMain.resources.getString("BrowseCollection"));
        executeScriptButton.setText(ClientMain.resources.getString("ExecuteScript"));
        infoButton.setText(ClientMain.resources.getString("Information"));
        replaceIfButton.setText(ClientMain.resources.getString("ReplaceIf"));
    }
}

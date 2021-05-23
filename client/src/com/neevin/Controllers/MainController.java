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

    public void visualizationButtonClick(ActionEvent event) throws IOException {
        changeView(event, "/com/neevin/Views/VisualisationView.fxml");
    }

    @Override
    public void updateLanguage() {
        super.updateLanguage();
        visualizationButton.setText(ClientMain.resources.getString("VisualizationArea"));
    }
}

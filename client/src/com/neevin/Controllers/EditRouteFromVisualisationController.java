package com.neevin.Controllers;

import com.neevin.ClientMain;
import com.neevin.DataModels.Route;
import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class EditRouteFromVisualisationController extends BaseController{
    /**
     * Id маршрута, который редактируется в данный момент
     */
    public static Route editingRoute;

    @FXML
    public Label idValueLabel;

    @FXML
    public Button backButton;
    @FXML
    public Button saveButton;

    @Override
    public void initialize(){
        super.initialize();


        idValueLabel.setText(String.valueOf(editingRoute.getId()));

    }

    @FXML
    private void backButtonClick(ActionEvent event) throws IOException {
        changeView(event, "/com/neevin/Views/VisualisationView.fxml");
    }

    @FXML
    private void saveButtonClick(ActionEvent event) throws IOException {
        // Сохранение...


        changeView(event, "/com/neevin/Views/VisualisationView.fxml");
    }
}

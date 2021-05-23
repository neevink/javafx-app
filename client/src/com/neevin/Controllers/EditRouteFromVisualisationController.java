package com.neevin.Controllers;

import com.neevin.ClientMain;
import com.neevin.DataModels.Coordinates;
import com.neevin.DataModels.Location;
import com.neevin.DataModels.LocationInteger;
import com.neevin.DataModels.Route;
import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditRouteFromVisualisationController extends BaseController{
    /**
     * Id маршрута, который редактируется в данный момент
     */
    public static Route editingRoute;

    @FXML
    public Button backButton;
    @FXML
    public Button saveButton;

    public Label idLabel;
    public Label idValueLabel;
    public Label creationDateLabel;
    public Label creationDateValueLabel;
    public Label nameLabel;
    public TextField nameValueField;
    public Label coordinateXLabel;
    public TextField coordinateXValueField;
    public Label coordinateYLabel;
    public TextField coordinateYValueField;
    public Label fromXLabel;
    public TextField fromXValueField;
    public Label fromYLabel;
    public TextField fromYValueField;
    public Label fromNameLabel;
    public TextField fromNameValueField;
    public Label toXLabel;
    public TextField toXValueField;
    public Label toYLabel;
    public TextField toYValueField;
    public Label toNameLabel;
    public TextField toNameValueField;
    public Label distanceLabel;
    public TextField distanceValueField;

    @Override
    public void initialize(){
        super.initialize();

        idValueLabel.setText(String.valueOf(editingRoute.getId()));
        creationDateValueLabel.setText(editingRoute.getCreationDate().toString());
        nameValueField.setText(editingRoute.getName());
        coordinateXValueField.setText(editingRoute.getCoordinates().getX().toString());
        coordinateYValueField.setText(String.valueOf(editingRoute.getCoordinates().getY()));
        fromXValueField.setText(String.valueOf(editingRoute.getFrom().getX()));
        fromYValueField.setText(String.valueOf(editingRoute.getFrom().getY()));
        fromNameValueField.setText(editingRoute.getFrom().getName());
        toXValueField.setText(String.valueOf(editingRoute.getTo().getX()));
        toYValueField.setText(String.valueOf(editingRoute.getTo().getY()));
        toNameValueField.setText(editingRoute.getTo().getName());
        distanceValueField.setText(editingRoute.getDistance().toString());

        if(!ClientMain.requestSender.getUserLogin().equals(editingRoute.getOwner())){
            nameValueField.setDisable(true);
            coordinateXValueField.setDisable(true);
            coordinateYValueField.setDisable(true);
            fromXValueField.setDisable(true);
            fromYValueField.setDisable(true);
            fromNameValueField.setDisable(true);
            toXValueField.setDisable(true);
            toYValueField.setDisable(true);
            toNameValueField.setDisable(true);
            distanceValueField.setDisable(true);

            saveButton.setDisable(true);
        }
    }

    @FXML
    private void backButtonClick(ActionEvent event) throws IOException {
        changeView(event, "/com/neevin/Views/VisualisationView.fxml");
    }

    @FXML
    private void saveButtonClick(ActionEvent event) throws IOException {
        Route r = new Route();
        try {
            r.setOwner(editingRoute.getOwner());
            r.setId(new Long(idValueLabel.getText()));
            r.setCreationDate(editingRoute.getCreationDate());
            r.setName(nameValueField.getText());
            r.setDistance(new Long(distanceValueField.getText()));

            Coordinates c = new Coordinates();
            c.setX(new Double(coordinateXValueField.getText()));
            c.setY(new Double(coordinateYValueField.getText()));
            r.setCoordinates(c);

            Location from = new Location();
            from.setX(new Double(fromXValueField.getText()));
            from.setY(new Double(fromYValueField.getText()));
            from.setName(fromNameValueField.getText());
            r.setFrom(from);

            LocationInteger to = new LocationInteger();
            to.setX(new Integer(toXValueField.getText()));
            to.setY(new Integer(toYValueField.getText()));
            to.setName(toNameValueField.getText());
            r.setTo(to);

            Request<?> request = new Request<Route>("update", r, ClientMain.requestSender.getUserLogin(), ClientMain.requestSender.getUserPassword());
            CommandResult result = ClientMain.requestSender.sendRequest(request);

            if(result.status == ResultStatus.OK){
                changeView(event, "/com/neevin/Views/VisualisationView.fxml");
            }
            else{
                createAlert(result.message);
            }
        }
        catch (NumberFormatException exc){
            createAlert(ClientMain.resources.getString("FieldsCantBeEmpty"));
        }
        catch (Exception exc){
            createAlert(exc.getMessage());
        }
    }

    @Override
    public void updateLanguage() {
        super.updateLanguage();
        idLabel.setText(ClientMain.resources.getString("Id"));
        creationDateLabel.setText(ClientMain.resources.getString("CreationDate"));
        nameLabel.setText(ClientMain.resources.getString("Name"));
        coordinateXLabel.setText(ClientMain.resources.getString("CoordinateX"));
        coordinateYLabel.setText(ClientMain.resources.getString("CoordinateY"));
        fromXLabel.setText(ClientMain.resources.getString("FromX"));
        fromYLabel.setText(ClientMain.resources.getString("FromY"));
        fromNameLabel.setText(ClientMain.resources.getString("FromName"));
        toXLabel.setText(ClientMain.resources.getString("ToX"));
        toYLabel.setText(ClientMain.resources.getString("ToY"));
        toNameLabel.setText(ClientMain.resources.getString("ToName"));
        distanceLabel.setText(ClientMain.resources.getString("Distance"));

        backButton.setText(ClientMain.resources.getString("Cancel"));
        saveButton.setText(ClientMain.resources.getString("Save"));
    }
}

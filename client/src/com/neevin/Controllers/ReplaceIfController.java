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
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Date;

public class ReplaceIfController extends BaseController{
    @FXML
    public Button backButton;
    @FXML
    public Button replaceIfGreaterButton;
    @FXML
    public Button replaceIfLowerButton;

    public Label idLabel;
    public TextField idValueField;
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

    public static String backPath = "/com/neevin/Views/MainView.fxml";

    @Override
    public void initialize(){
        super.initialize();
    }

    @FXML
    private void backButtonClick(ActionEvent event) throws IOException {
        changeView(event, backPath);
    }

    private Route getNewRoute() {
        Route r = new Route();
        try {
            r.setId(new Long(idValueField.getText()));
            r.setCreationDate(new Date());
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

            return r;
        }
        catch (NumberFormatException exc){
            createAlert(ClientMain.resources.getString("FieldsCantBeEmpty"));
        }
        catch (Exception exc){
            createAlert(exc.getMessage());
        }
        return null;
    }

    @Override
    public void updateLanguage() {
        super.updateLanguage();
        idLabel.setText(ClientMain.resources.getString("Id"));
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
        replaceIfGreaterButton.setText(ClientMain.resources.getString("ReplaceIfGreater"));
        replaceIfLowerButton.setText(ClientMain.resources.getString("ReplaceIfLower"));
    }

    public void replaceIfGreaterButtonClick(ActionEvent event) throws IOException {
        Route newRoute = getNewRoute();
        if(newRoute == null){
            return;
        }

        Request<?> request = new Request<Route>("replace_if_greater", newRoute, ClientMain.requestSender.getUserLogin(), ClientMain.requestSender.getUserPassword());
        CommandResult result = ClientMain.requestSender.sendRequest(request);
        createAlert(result.message);
        if(result.status == ResultStatus.OK){
            changeView(event, backPath);
        }
    }

    public void replaceIfLessButtonClick(ActionEvent event) throws IOException {
        Route newRoute = getNewRoute();
        if(newRoute == null){
            return;
        }

        Request<?> request = new Request<Route>("replace_if_lowe", newRoute, ClientMain.requestSender.getUserLogin(), ClientMain.requestSender.getUserPassword());
        CommandResult result = ClientMain.requestSender.sendRequest(request);
        createAlert(result.message);
        if(result.status == ResultStatus.OK){
            changeView(event, backPath);
        }
    }
}

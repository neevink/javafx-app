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

public class CreateRouteController extends BaseController{
    @FXML
    public Button backButton;
    @FXML
    public Button createButton;

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

    public static String backPath = "/com/neevin/Views/ShowView.fxml";

    @Override
    public void initialize(){
        super.initialize();
    }

    @FXML
    private void backButtonClick(ActionEvent event) throws IOException {
        changeView(event, backPath);
    }

    @Override
    public void updateLanguage() {
        super.updateLanguage();

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
        createButton.setText(ClientMain.resources.getString("Create"));
    }

    public void createButtonClick(ActionEvent event) {
        Route r = new Route();
        try {
            r.setOwner(ClientMain.requestSender.getUserLogin());
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

            Request<?> request = new Request<Route>("insert", r, ClientMain.requestSender.getUserLogin(), ClientMain.requestSender.getUserPassword());
            CommandResult result = ClientMain.requestSender.sendRequest(request);

            if(result.status == ResultStatus.OK){
                changeView(event, backPath);
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
}

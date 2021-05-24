package com.neevin.Controllers;

import com.neevin.ClientMain;
import com.neevin.DataModels.Route;
import com.neevin.DataModels.RouteViewModel;
import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowController extends BaseController{
    @FXML
    private TableView<RouteViewModel> table;
    @FXML
    private TableColumn<Route, String> ownerColumn;
    @FXML
    private TableColumn<Route, String> idColumn;
    @FXML
    private TableColumn<Route, String> dateColumn;
    @FXML
    private TableColumn<Route, String> nameColumn;
    @FXML
    private TableColumn<Route, String> coordinateXColumn;
    @FXML
    private TableColumn<Route, String> coordinateYColumn;
    @FXML
    private TableColumn<Route, String> fromXColumn;
    @FXML
    private TableColumn<Route, String> fromYColumn;
    @FXML
    private TableColumn<Route, String> fromNameColumn;
    @FXML
    private TableColumn<Route, String> toXColumn;
    @FXML
    private TableColumn<Route, String> toYColumn;
    @FXML
    private TableColumn<Route, String> toNameColumn;
    @FXML
    private TableColumn<Route, String> distanceColumn;

    private Thread thread;

    // Выпили потом
    ObservableList<String> fields = FXCollections.observableArrayList(
            ClientMain.resources.getString("Owner"),
            "#",
            ClientMain.resources.getString("CreationDate"),
            ClientMain.resources.getString("Name"),
            ClientMain.resources.getString("CoordinateX"),
            ClientMain.resources.getString("CoordinateY"),
            ClientMain.resources.getString("FromX"),
            ClientMain.resources.getString("FromY"),
            ClientMain.resources.getString("FromName"),
            ClientMain.resources.getString("ToX"),
            ClientMain.resources.getString("ToY"),
            ClientMain.resources.getString("ToName"),
            ClientMain.resources.getString("Distance")
    );

    @FXML
    private Button backButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button addButton;

    @Override
    public void initialize() {
        super.initialize();

        table.setPlaceholder(new Label(""));
        table.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    EditRouteFromVisualisationController.editingRoute = table.getSelectionModel().getSelectedItem().getRoute();
                    try {
                        EditRouteFromVisualisationController.backPath = "/com/neevin/Views/ShowView.fxml";
                        changeView(table, "/com/neevin/Views/EditRouteFromVisualisationView.fxml");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });

        thread = new Thread(() -> {
            try {
                while (true) {
                    Platform.runLater(this::updateTable);
                    Thread.sleep(2000);
                }
            }
            catch(InterruptedException e) {}
        });
        thread.start();
    }

    private void updateTable() {
        Request<?> request = new Request<Object>("show", null, ClientMain.requestSender.getUserLogin(), ClientMain.requestSender.getUserPassword());
        CommandResult result = ClientMain.requestSender.sendRequest(request);

        if(result.status == ResultStatus.OK){
            ObservableList<RouteViewModel> tableRoutes = FXCollections.observableArrayList();

            ArrayList<RouteViewModel> arr = new ArrayList<>();
            for(var e : (List<Route>) result.entity){
                arr.add(new RouteViewModel(e));
            }

            tableRoutes.addAll(arr);
            table.setItems(tableRoutes);
        }
        else {
            createAlert(result.message);
        }
    }

    @FXML
    public void backButtonClick(ActionEvent actionEvent) throws IOException {
        changeView(actionEvent, "/com/neevin/Views/MainView.fxml");
    }

    @FXML
    public void clearButtonClick(ActionEvent actionEvent) throws IOException {
        // Очистка коллекции
    }

    @FXML
    public void addButtonClick(ActionEvent actionEvent) throws IOException {
        // Открыть окно для добавления
    }

    void initializeTable(){
        ownerColumn.setCellValueFactory(new PropertyValueFactory("owner"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        coordinateXColumn.setCellValueFactory(new PropertyValueFactory<>("coordinateX"));
        coordinateYColumn.setCellValueFactory(new PropertyValueFactory<>("coordinateY"));
        fromXColumn.setCellValueFactory(new PropertyValueFactory<>("fromX"));
        fromYColumn.setCellValueFactory(new PropertyValueFactory<>("fromY"));
        fromNameColumn.setCellValueFactory(new PropertyValueFactory<>("fromName"));
        toXColumn.setCellValueFactory(new PropertyValueFactory<>("toX"));
        toYColumn.setCellValueFactory(new PropertyValueFactory<>("toY"));
        toNameColumn.setCellValueFactory(new PropertyValueFactory<>("toName"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
    }

    @Override
    public void updateLanguage() {
        super.updateLanguage();
        backButton.setText(ClientMain.resources.getString("Back"));
        clearButton.setText(ClientMain.resources.getString("Clear"));
        addButton.setText(ClientMain.resources.getString("Add"));
    }
}

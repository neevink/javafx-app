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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @FXML
    private TextField commonField;

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
                if(table.getSelectionModel().getSelectedItem() == null){
                    return;
                }
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
                    Thread.sleep(1000);
                }
            }
            catch(InterruptedException e) {}
        });
        thread.start();
    }

    protected List<Route> collection = null;

    private void updateTable() {
        Request<?> request = new Request<Object>("show", null, ClientMain.requestSender.getUserLogin(), ClientMain.requestSender.getUserPassword());
        CommandResult result = ClientMain.requestSender.sendRequest(request);

        if(filterStartsWithName){
            result.entity = ((List<Route>) result.entity).stream()
                    .filter(x -> x.getName().startsWith(startsWithName))
                    .sorted(Comparator.comparing(Route::getCoordinates).reversed())
                    .collect(Collectors.toList());
        }

        if(filterDistanceGreater){
            result.entity = ((List<Route>) result.entity).stream()
                    .filter(x -> x.getDistance() > distanceGreater)
                    .sorted(Comparator.comparing(Route::getCoordinates).reversed())
                    .collect(Collectors.toList());
        }

        if(result.status == ResultStatus.OK){
            if(collection != null && collection.equals((List<Route>) result.entity)){
                return;
            }
            collection = (List<Route>) result.entity;

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
        Request<?> request = new Request<String>("clear", null, ClientMain.requestSender.getUserLogin(), ClientMain.requestSender.getUserPassword());
        CommandResult result = ClientMain.requestSender.sendRequest(request);

        if(result.status != ResultStatus.OK){
            createAlert(result.message);
        }
    }

    @FXML
    public void addButtonClick(ActionEvent actionEvent) throws IOException {
        CreateRouteController.backPath = "/com/neevin/Views/ShowView.fxml";
        changeView(actionEvent, "/com/neevin/Views/CreateRouteView.fxml");
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

    public void removeLessThenKeyClick(ActionEvent event) {
        long id = -1;
        try {
            id = Long.parseLong(commonField.getText());
        }
        catch (NumberFormatException exc){
            createAlert(ClientMain.resources.getString("FieldsCantBeEmpty"));
            return;
        }

        Request<?> request = new Request<Long>("remove_lower_key", id, ClientMain.requestSender.getUserLogin(), ClientMain.requestSender.getUserPassword());
        CommandResult result = ClientMain.requestSender.sendRequest(request);

        if(result.status != ResultStatus.OK){
            createAlert("Произошла ошибка: " + result.message);
        }
    }

    boolean filterStartsWithName = false;
    String startsWithName = "";

    boolean filterDistanceGreater = false;
    long distanceGreater = -1;

    public void startsWithClick(ActionEvent event) {
        startsWithName = commonField.getText();
        filterStartsWithName = true;
        filterDistanceGreater = false;
    }

    public void distanceGreaterThenClick(ActionEvent event) {
        long distance = -1;
        try {
            distance = Long.parseLong(commonField.getText());
        }
        catch (NumberFormatException exc){
            createAlert(ClientMain.resources.getString("FieldsCantBeEmpty"));
            return;
        }
        distanceGreater = distance;
        filterDistanceGreater = true;
        filterStartsWithName = false;
    }

    public void dropClick(ActionEvent event) {
        filterStartsWithName = false;
        filterDistanceGreater = false;

    }
}

package com.neevin.Controllers;

import com.neevin.ClientMain;
import com.neevin.DataModels.Route;
import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class VisualisationController extends BaseController{
    @FXML
    private AnchorPane pane;
    @FXML
    private Button backButton;

    private Thread thread;

    @Override
    public void initialize() {
        super.initialize();

        thread = new Thread(() -> {
            try {
                while (true) {
                    Platform.runLater(this::drawRoutes);
                    Thread.sleep(1000);
                }
            }
            catch(InterruptedException e) {}
        });
        thread.start();
    }

    private void drawRoutes() {
        Request<?> request = new Request<Object>("show", null, ClientMain.requestSender.getUserLogin(), ClientMain.requestSender.getUserPassword());
        CommandResult result = ClientMain.requestSender.sendRequest(request);

        pane.getChildren().clear();

        if(result.status == ResultStatus.OK){
            int screenSizeX = 600;
            int screenSizeY = 400;
            var entities = calculatePositions((ArrayList<Route>)result.entity, screenSizeX, screenSizeY);

            for(Circle c : entities){
                ScaleTransition st = new ScaleTransition(Duration.millis(500), c);
                st.setByX(0.25f);
                st.setByY(0.25f);
                st.setCycleCount(1000);
                st.setAutoReverse(true);

                st.play();
                pane.getChildren().add(c);
            }
        }
        else{
            createAlert(result.message);
        }
    }

    private ArrayList<Circle> calculatePositions(ArrayList<Route> array, int screenSizeX, int screenSizeY){
        final int OFFSET_X = 50; // Отступ от краёв экрана по X
        final int OFFSET_Y = 70; // Отступ от краёв экрана по Y
        final int CIRCLE_SIZE = 15; // Радиус кружков-элементов
        final Color redColor = new Color(0.8,0.36,0.36,1);
        final Color greenColor = new Color(0.54,0.8,0.36,1);

        if(array.size() == 0){
            return new ArrayList<>();
        }

        double maxCoordinateX = array.stream().map(x -> Math.abs(x.getCoordinates().getX())).mapToDouble(x -> x).max().getAsDouble();
        double maxCoordinateY = array.stream().map(x -> Math.abs(x.getCoordinates().getY())).mapToDouble(y -> y).max().getAsDouble();

        ArrayList<Circle> newCoordinates = new ArrayList<>();
        for(Route r : array){
            int newPositionX = screenSizeX/2;
            if(maxCoordinateX != 0){
                newPositionX = (int) (screenSizeX/2 + r.getCoordinates().getX().doubleValue()/maxCoordinateX*(screenSizeX/2 - OFFSET_X));
            }
            int newPositionY = screenSizeY/2;
            if(maxCoordinateY != 0){
                newPositionY = (int) (screenSizeY/2 + r.getCoordinates().getY()/maxCoordinateY*(screenSizeY/2 - OFFSET_Y));
            }

            Circle c;
            // Если объект принадлежит этому пользователю, то зелёным нарисовать, иначе красным
            if(r.getOwner().equals(ClientMain.requestSender.getUserLogin())){
                c = new Circle(newPositionX, newPositionY, CIRCLE_SIZE, greenColor);
            }
            else {
                c = new Circle(newPositionX, newPositionY, CIRCLE_SIZE, redColor);
            }
            c.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getClickCount() >= 2) {
                        EditRouteFromVisualisationController.editingRoute = r;
                        try {
                            changeView(pane, "/com/neevin/Views/EditRouteFromVisualisationView.fxml");
                        } catch (IOException exc) {
                            System.out.println("Всё хана, Кирюха ты юзаешь вьюху, которой нет! " + exc.getMessage());
                            exc.printStackTrace();
                        }
                    }
                }
            });
            newCoordinates.add(c);
        }

        return newCoordinates;
    }

    @FXML
    private void backButtonClick(javafx.event.ActionEvent event) throws IOException {
        changeView(event, "/com/neevin/Views/MainView.fxml");
    }

    @Override
    public void updateLanguage() {
        super.updateLanguage();
        backButton.setText(ClientMain.resources.getString("Back"));
    }
}

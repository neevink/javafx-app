package com.neevin.DataModels;

public class RouteViewModel {
    private Route route;

    public RouteViewModel(Route route){
        this.route = route;
    }

    public String getOwner(){
        return route.getOwner();
    }

    public String getId(){
        return String.valueOf(route.getId());
    }

    public String getDate(){
        return route.getCreationDate().toString();
    }

    public String getName(){
        return route.getName();
    }

    public String getCoordinateX(){
        return route.getCoordinates().getX().toString();
    }

    public String getCoordinateY(){
        return String.valueOf(route.getCoordinates().getY());
    }

    public String getFromX(){
        return String.valueOf(route.getFrom().getX());
    }

    public String getFromY(){
        return String.valueOf(route.getFrom().getY());
    }

    public String getFromName(){
        return String.valueOf(route.getFrom().getName());
    }

    public String getToX(){
        return String.valueOf(route.getTo().getX());
    }

    public String getToY(){
        return String.valueOf(route.getTo().getY());
    }

    public String getToName(){
        return String.valueOf(route.getTo().getName());
    }

    public String getDistance(){
        return String.valueOf(route.getDistance());
    }

    public Route getRoute() {
        return this.route;
    }
}

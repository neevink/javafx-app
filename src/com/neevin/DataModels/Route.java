package com.neevin.DataModels;

import java.util.Date;
import java.util.Objects;

public class Route implements Comparable<Route> {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private LocationInteger to; //Поле может быть null
    private Long distance; //Поле может быть null, Значение поля должно быть больше 1

    private static final long MIN_ID_VALUE = 0;
    private static final long MIN_DISTANCE_VALUE = 1;

    public Route(){

    }

    public Route(long id, String name, Coordinates coordinates, Location from, LocationInteger to, Long distance) {
        setId(id);
        setName(name);
        setCoordinates(coordinates);
        setFrom(from);
        setTo(to);
        setDistance(distance);

        creationDate = new Date();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        if(id <= MIN_ID_VALUE){
            throw new IllegalArgumentException("Значение id добжно быть больше " + MIN_ID_VALUE + "!");
        }

        this.id = id;
    }

    String getName(){
        return this.name;
    }

    void setName(String name){
        if(name == null){
            throw new IllegalArgumentException("Имя не может быть null!");
        }
        if(name.length() == 0){
            throw new IllegalArgumentException("Имя не может быть пустым!");
        }

        this.name = name;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if(coordinates == null){
            throw new IllegalArgumentException("Координаты не могут быть null!");
        }

        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        if(creationDate == null){
            throw new IllegalArgumentException("Дата создания не может быть null!");
        }
        this.creationDate = creationDate;
    }

    public Location getFrom() {
        return this.from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public LocationInteger getTo() {
        return this.to;
    }

    public void setTo(LocationInteger to) {
        this.to = to;
    }

    public Long getDistance() {
        return this.distance;
    }

    public void setDistance(Long distance) {
        if(distance != null && distance.longValue() <= MIN_DISTANCE_VALUE){
            throw new IllegalArgumentException("Дистанция должна быть больше " + MIN_DISTANCE_VALUE + "!");
        }

        this.distance = distance;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.name, this.distance, this.creationDate, this.coordinates, this.to, this.from);
    }

    @Override
    public boolean equals(Object other){
        if(this == other){
            return true;
        }

        if(other == null){
            return  false;
        }

        // Для этой сущности объекты разных классов не могут быть равны
        if(this.getClass() != other.getClass()){
            return false;
        }

        Route o = (Route) other;
        return this.id == o.id && this.from.equals(o.from) && this.to.equals(o.to) && this.coordinates.equals(o.coordinates)
                && this.creationDate.equals(o.creationDate) && this.distance.equals(o.distance) && this.name.equals(o.name);
    }

    @Override
    public String toString(){
        return String.format("Маршрут #%d \'%s\' длиной в %d от %s", this.id, this.name, this.distance, this.creationDate.toString()) + '\n' +
                "Текущая координата: " + this.coordinates.toString() + '\n' +
                "Движение из: " + this.from.toString() + '\n' +
                "Движение в: " + this.to.toString();
    }

    @Override
    public int compareTo(Route o) {
        throw new RuntimeException();

    }
}

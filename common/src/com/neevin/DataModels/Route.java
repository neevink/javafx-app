package com.neevin.DataModels;

import java.util.Date;
import java.util.Objects;

/**
 * Маршрут
 */
public class Route implements Comparable<Route> {
    /**
     * Идентификатор маршрута
     */
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    /**
     * Название маршрута
     */
    private String name; //Поле не может быть null, Строка не может быть пустой
    /**
     * Текущая координата маршрута
     */
    private Coordinates coordinates; //Поле не может быть null
    /**
     * Дата создания маршрута
     */
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /**
     * Пункт отправления
     */
    private Location from; //Поле может быть null
    /**
     * Пункт прибытия
     */
    private LocationInteger to; //Поле может быть null
    /**
     * Дистанция
     */
    private Long distance; //Поле может быть null, Значение поля должно быть больше 1


    /**
     * Минимальное значение идентификатора
     */
    private static final long MIN_ID_VALUE = 1;
    /**
     * Минимальное значение дистанции
     */
    private static final long MIN_DISTANCE_VALUE = 2;
    {
        creationDate = new Date();
    }

    public Route(){

    }

    public Route(long id, String name, Coordinates coordinates, Location from, LocationInteger to, Long distance) {
        setId(id);
        setName(name);
        setCoordinates(coordinates);
        setFrom(from);
        setTo(to);
        setDistance(distance);
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        if(id < MIN_ID_VALUE){
            throw new IllegalArgumentException("Значение id добжно быть больше или равно " + MIN_ID_VALUE + "!");
        }

        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
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
        if(distance != null && distance.longValue() < MIN_DISTANCE_VALUE){
            throw new IllegalArgumentException("Дистанция должна быть больше или равна " + MIN_DISTANCE_VALUE + "!");
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
        String coord = this.coordinates != null ? this.coordinates.toString() : "отсутствует";
        String f = this.from != null ? this.from.toString() : "отсутствует";
        String t = this.to != null ? this.to.toString() : "отсутствует";

        return String.format("Маршрут #%d \"%s\" длиной в %d от %s", this.id, this.name, this.distance, this.creationDate.toString()) + '\n' +
                "Текущая координата: " + coord + '\n' +
                "Движение из: " + f + '\n' +
                "Движение в: " + t;
    }

    @Override
    public int compareTo(Route o) {
        if(distance != null && o.distance != null && distance.compareTo(o.distance) != 0){
            return distance.compareTo(o.distance);
        }

        if(this.name != null && o.name != null && this.name.compareTo(o.name) != 0){
            return this.name.compareTo(o.name);
        }

        if(this.coordinates.compareTo(o.coordinates) != 0){
            return this.coordinates.compareTo(o.coordinates);
        }

        if(this.from != null && o.from != null && this.from.compareTo(o.from) != 0){
            return this.from.compareTo(o.from);
        }

        if(this.to != null && o.to != null &&  this.to.compareTo(o.to) != 0){
            return this.to.compareTo(o.to);
        }
        else{
            return 0;
        }
    }
}

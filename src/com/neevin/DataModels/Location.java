package com.neevin.DataModels;

import java.util.Objects;

public class Location implements Comparable<Location> {
    private double x;
    private Double y; // Поле не может быть null
    private String name; // Строка не может быть пустой, Поле может быть null

    public Location(){

    }

    public Location(double x, Double y, String name){
        setX(x);
        setY(y);
        setName(name);
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public Double getY() {
        return this.y;
    }

    public void setY(Double y) {
        if(y == null){
            throw  new IllegalArgumentException("Координата y не может быть null!");
        }

        this.y = y;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if(name != null && name.length() == 0){
            throw new IllegalArgumentException("Имя не может быть пустым!");
        }

        this.name = name;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.x, this.y, this.name);
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

        Location o = (Location) other;
        return this.x == o.x && this.y.equals(o.y) && this.name.equals(o.name);
    }

    @Override
    public String toString(){
        return String.format("{\"%s\"; %.2f; %.2f}", this.name, this.x, this.y.doubleValue());
    }

    @Override
    public int compareTo(Location other) {
        if(this.equals(other)){
            return 0;
        }

        // Находим квадрат модуля вектора координат
        double t = this.x * this.x + this.y.doubleValue() * this.y.doubleValue();
        double o = other.x * other.x + other.y.doubleValue() * other.y.doubleValue();

        // Если квадрат модулей равен, то сравниваем по имени
        if(t == o){
            return this.name.compareTo(other.name);
        }
        else if(o > t){
            return 1;
        }
        else {
            return -1;
        }
    }
}
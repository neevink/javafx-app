package com.neevin.DataModels;

import java.util.Objects;

/**
 * Координаты
 */
public class Coordinates implements Comparable<Coordinates> {
    /**
     * Координата x
     */
    private Double x; //Поле не может быть null
    /**
     * Координата y
     */
    private double y; //Максимальное значение поля: 68

    /**
     * Максимальное значение координаты y
     */
    private static final double MAX_Y_VALUE = 68;

    public Coordinates(){ }

    public Coordinates(Double x, double y){
        setX(x);
        setY(y);
    }

    public Double getX(){
        return this.x;
    }

    public void setX(Double x){
        if(x == null){
            throw new IllegalArgumentException("Координата x не может быть null!");
        }

        this.x = x;
    }

    public double getY(){
        return this.y;
    }

    public void setY(double y){
        if(y > MAX_Y_VALUE){
            throw new IllegalArgumentException("Координата y не может быть больше " + MAX_Y_VALUE + "!");
        }

        this.y = y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.x, this.y);
    }

    @Override
    public boolean equals(Object other){
        if(this == other){
            return true;
        }

        if(other == null){
            return false;
        }

        // Для этой сущности объекты разных классов не могут быть равны
        if(this.getClass() != other.getClass()){
            return false;
        }
        Coordinates o = (Coordinates) other;
        return this.x.equals(o.x) && this.y == o.y;
    }

    @Override
    public String toString(){
        return String.format("{%.2f; %.2f}", this.x.doubleValue(), this.y);
    }

    @Override
    public int compareTo(Coordinates other) {
        // Находим квадрат модуля вектора координат
        double t = Math.pow(this.x.doubleValue(), 2) + Math.pow(this.y, 2);
        double o = Math.pow(other.x.doubleValue(), 2) + Math.pow(other.y, 2);

        // Потом сравниваем модули
        if(t == o){
            return 0;
        }
        else if(o > t){
            return 1;
        }
        else {
            return -1;
        }
    }
}



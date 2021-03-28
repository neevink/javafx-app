package com.neevin.DataModels;


import java.util.Objects;

/**
 * Местоположение с целочисленными координатами
 */
public class LocationInteger implements Comparable<LocationInteger> { // Тут вышла ошибка генератора, сгенерированы два разных класса Location
    /**
     * Координата x
     */
    private int x;
    /**
     * Координата y
     */
    private Integer y; //Поле не может быть null
    /**
     * Название целочисленного местоположения
     */
    private String name; //Длина строки не должна быть больше 322, Поле не может быть null

    /**
     * Максимальная длина имени местоположения
     */
    private final static int MAX_NAME_LENGTH = 322;

    public LocationInteger(){ }

    public  LocationInteger(int x, Integer y, String name){
        setX(x);
        setY(y);
        setName(name);
    }

    public int getX(){
        return this.x;
    }

    public void setX(int x){
        this.x = x;
    }

    public Integer getY(){
        return this.y;
    }

    public void setY(Integer y){
        if(y == null){
            throw new IllegalArgumentException("Координа y не может быть null!");
        }

        this.y = y;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        if(name == null){
            throw new IllegalArgumentException("Имя не может быть null!");
        }
        if(name.length() > MAX_NAME_LENGTH){
            throw new IllegalArgumentException("Длина имени не может быть больше " + MAX_NAME_LENGTH + "!");
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
            return false;
        }

        // Для этой сущности объекты разных классов не могут быть равны
        if(getClass() != other.getClass()){
            return false;
        }

        LocationInteger o = (LocationInteger) other;
        return this.name.equals(o.name) && this.x == o.x && this.y.equals(o.y);
    }

    @Override
    public String toString(){
        return String.format("{\"%s\"; %d; %d}", this.name, this.x, this.y.intValue());
    }

    @Override
    public int compareTo(LocationInteger other) {
        if(this.equals(other)){
            return 0;
        }

        // Находим квадрат модуля вектора координат
        long t = this.x * this.x + this.y.intValue() * this.y.intValue();
        long o = other.x * other.x + other.y.intValue() * other.y.intValue();

        // Если модуль равен, то сравниваем по имени
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

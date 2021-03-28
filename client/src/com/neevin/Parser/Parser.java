package com.neevin.Parser;

import com.neevin.DataModels.Coordinates;
import com.neevin.DataModels.Location;
import com.neevin.DataModels.LocationInteger;

import java.util.AbstractList;


/**
 * Парсер объектов
 */
public abstract class Parser {

    /**
     * Спарсить число типа long
     * @param t токен типа целое число
     * @return Возвращает спарсенное значение long
     */
    public static long parseLong(Token t) throws Exception {
        if(t == null){
            throw new IllegalArgumentException("Токен не может быть null!");
        }

        if(t.type != TokenType.INTEGER_NUMBER){
            throw new IllegalArgumentException("Ожидается целое число.");
        }

        long l;
        try {
            l = Long.parseLong(t.object);
        }
        catch (Exception e){
            throw new Exception("Слишком длинное число для типа long!");
        }

        return l;
    }

    /**
     * Спарсить число типа int
     * @param t токен типа целое число
     * @return Возвращает спарсенное значение int
     */
    public static int parseInt(Token t) throws Exception {
        if(t == null){
            throw new IllegalArgumentException("Токен не может быть null!");
        }

        if(t.type != TokenType.INTEGER_NUMBER){
            throw new IllegalArgumentException("Ожидается целое число.");
        }

        int l;
        try {
            l = Integer.parseInt(t.object);
        }
        catch (Exception e){
            throw new Exception("Слишком длинное число для типа int!");
        }

        return l;
    }

    /**
     * @param t токен типа дробное число
     * @return Возвращает спарсенное значение double
     * @throws Exception
     */
    public static double parseDouble(Token t) throws Exception {
        if(t == null){
            throw new IllegalArgumentException("Токен не может быть null!");
        }

        if(t.type != TokenType.FLOAT_NUMBER && t.type != TokenType.INTEGER_NUMBER){
            throw new IllegalArgumentException("Ожидается целое или дробное число.");
        }

        double d;
        try {
            d = Double.parseDouble(t.object);
        }
        catch (Exception e){
            throw new Exception("Слишком длинное число для типа double!");
        }

        return d;
    }

    /**
     * @param t токен типа строка
     * @return Возвращает спарсенное значение String
     * @throws Exception
     */
    public static String parseString(Token t) throws Exception {
        if(t == null){
            throw new IllegalArgumentException("Токен не может быть null!");
        }

        if(t.type != TokenType.STRING){
            throw new IllegalArgumentException("Ожидается строка в ковычках.");
        }

        return t.object;
    }


    /**
     * @param tokens Список токенов с полями объекта coordinates
     * @return Возвращает спарсенный объект Coordinates
     * @throws Exception
     */
    public static Coordinates parseCoordinates(AbstractList<Token> tokens) throws Exception {
        if(tokens == null){
            throw new IllegalArgumentException("Токен не может быть null!");
        }

        if(tokens.size() != 2){
            throw new IllegalArgumentException("Ожидается 2 аргумента.");
        }

        double x, y;
        try{
            x = parseDouble(tokens.get(0));
        }
        catch (Exception e){
            throw new Exception("Ошибка парсинга аргумента x: " + e.getMessage());
        }

        try{
            y = parseDouble(tokens.get(1));
        }
        catch (Exception e){
            throw new Exception("Ошибка парсинга аргумента y: " + e.getMessage());
        }

        return new Coordinates(x, y);
    }


    /**
     * @param tokens Список токенов с полями объекта location
     * @return Возвращает спарсенный объект Location
     * @throws Exception
     */
    public static Location parseLocation(AbstractList<Token> tokens) throws Exception {
        if(tokens == null){
            throw new IllegalArgumentException("Токен не может быть null!");
        }

        if(tokens.size() != 3){
            throw new IllegalArgumentException("Ожидается 3 аргумента.");
        }

        double x, y;
        String name;
        try{
            x = parseDouble(tokens.get(0));
        }
        catch (Exception e){
            throw new Exception("Ошибка парсинга аргумента x: " + e.getMessage());
        }

        try{
            y = parseDouble(tokens.get(1));
        }
        catch (Exception e){
            throw new Exception("Ошибка парсинга аргумента y: " + e.getMessage());
        }

        try{
            name = parseString(tokens.get(2));
        }
        catch (Exception e){
            throw new Exception("Ошибка парсинга аргумента name: " + e.getMessage());
        }

        return new Location(x, y, name);
    }

    /**
     * @param tokens Список токенов с полями объекта locationInteger
     * @return Возвращает спарсенный объект LocationInteger
     * @throws Exception
     */
    public static LocationInteger parseLocationInteger(AbstractList<Token> tokens) throws Exception {
        if(tokens == null){
            throw new IllegalArgumentException("Токен не может быть null!");
        }

        if(tokens.size() != 3){
            throw new IllegalArgumentException("Ожидается 3 аргумента.");
        }

        int x, y;
        String name;
        try{
            x = parseInt(tokens.get(0));
        }
        catch (Exception e){
            throw new Exception("Ошибка парсинга аргумента x: " + e.getMessage());
        }

        try{
            y = parseInt(tokens.get(1));
        }
        catch (Exception e){
            throw new Exception("Ошибка парсинга аргумента y: " + e.getMessage());
        }

        try{
            name = parseString(tokens.get(2));
        }
        catch (Exception e){
            throw new Exception("Ошибка парсинга аргумента name: " + e.getMessage());
        }

        return new LocationInteger(x, y, name);
    }
}

package com.neevin.Parser;

import com.neevin.DataModels.Coordinates;
import com.neevin.DataModels.Location;
import com.neevin.DataModels.LocationInteger;
import com.neevin.DataModels.Route;

import java.util.AbstractList;
import java.util.Scanner;

/**
 * Помощник ввода, позволяет запросить у пользователя ввод полей определённого объекта
 */
public class InputHelper {

    /**
     * Запросить ввод координат
     * @param route Сюда будет установлена спарсенная координата
     * @param s Сканнер, откуда читать ввод
     */
    public static void receiveCoordinates(Route route, Scanner s) {
        boolean coordinatesParsed = false;

        System.out.println("Введите поля объекта Coordinates через пробел: x(дробное число) y(дробное число).");
        // Пока координаты не спарсены успешно
        while (!coordinatesParsed){
            try{
                AbstractList<Token> coordinatesTokens = Tokenizer.tokenize(s.nextLine());
                InputHelper.displayInput(coordinatesTokens);
                Coordinates coordinates = null;
                if(coordinatesTokens.size() != 0) {
                    coordinates = Parser.parseCoordinates(coordinatesTokens);
                }
                route.setCoordinates(coordinates);
                coordinatesParsed = true;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Повторите ввод.");
            }
        }
    }

    /**
     * Запросить ввод пункта отправления
     * @param route Сюда будет установлен спарсенный пункт
     * @param s Сканнер, откуда читать ввод
     */
    public static void receiveFrom(Route route, Scanner s) {
        boolean locationParsed = false;

        System.out.println("Введите поля объекта Location через пробел: x(дробное число) y(дробное число) name(строка в ковычках). Введите пусую строку, чтобы задать значение null.");
        // Пока координаты не спарсены успешно
        while (!locationParsed){
            try{
                AbstractList<Token> fromTokens = Tokenizer.tokenize(s.nextLine());
                InputHelper.displayInput(fromTokens);
                Location from = null;
                if(fromTokens.size() != 0) {
                    from = Parser.parseLocation(fromTokens);
                }
                route.setFrom(from);
                locationParsed = true;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Повторите ввод.");
            }
        }
    }

    /**
     * Запросить ввод пункта прибытия
     * @param route Сюда будет установлен спарсенный пункт
     * @param s Сканнер, откуда читать ввод
     */
    public static void receiveTo(Route route, Scanner s) {
        boolean locationParsed = false;

        System.out.println("Введите поля объекта Location через пробел: x(дробное число) y(дробное число) name(строка в ковычках). Введите пусую строку, чтобы задать значение null.");
        // Пока координаты не спарсены успешно
        while (!locationParsed){
            try{
                AbstractList<Token> toTokens = Tokenizer.tokenize(s.nextLine());
                InputHelper.displayInput(toTokens);
                LocationInteger to = null;
                if(toTokens.size() != 0) {
                    to = Parser.parseLocationInteger(toTokens);
                }
                route.setTo(to);
                locationParsed = true;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Повторите ввод.");
            }
        }
    }

    /**
     * Запросить ввод id объекта Route
     * @param route Сюда будет установлен спарсенный id
     * @param t токен содержащий id
     */
    public static void receiveId(Route route, Token t) throws Exception {
        long id;
        try{
            id = Parser.parseLong(t);
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента name не удался. " + e.getMessage());
        }
        route.setId(id);
    }

    /**
     * Запросить ввод имени объекта Route
     * @param route Сюда будет установлено спарсенное имя
     * @param t токен содержащий имя
     */
    public static void receiveName(Route route, Token t) throws Exception {
        String name;
        try{
            name = Parser.parseString(t);
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента name не удался. " + e.getMessage());
        }
        route.setName(name);
    }

    /**
     * Запросить ввод дистанции объекта Route
     * @param route Сюда будет установлена спарсенная координата
     * @param t токен содержащий дистанцию
     */
    public static void receiveDistance(Route route, Token t) throws Exception {
        long distance;
        try{
            distance = Parser.parseLong(t);
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента distance не удался. " + e.getMessage());
        }
        route.setDistance(distance);
    }

    /**
     * Вывести информацию о пользовательском вводе
     * @param tokens Список введённых токенов
     */
    public static void displayInput(AbstractList<Token> tokens){
        String s = "Введено: ";
        for (Token e : tokens){
            if(e.type == TokenType.STRING){
                s += String.format("\"%s\" ", e.object);
            }
            else {
                s += e.object + " ";
            }
        }

        System.out.println(s);
    }
}

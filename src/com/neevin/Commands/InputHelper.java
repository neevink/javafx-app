package com.neevin.Commands;

import com.neevin.DataModels.Coordinates;
import com.neevin.DataModels.Location;
import com.neevin.DataModels.LocationInteger;
import com.neevin.DataModels.Route;
import com.neevin.Parser.Parser;
import com.neevin.Parser.Token;
import com.neevin.Parser.Tokenizer;

import java.util.AbstractList;
import java.util.Scanner;

public class InputHelper {

    public static void receiveCoordinates(Route route, Scanner s) {
        boolean coordinatesParsed = false;

        System.out.println("Введите поля объекта Coordinates через пробел: x(дробное число) y(дробное число). Введите пусую строку, чтобы задать значение null.");
        // Пока координаты не спарсены успешно
        while (!coordinatesParsed){
            try{
                AbstractList<Token> coordinatesTokens = Tokenizer.tokenize(s.nextLine());
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

    public static void receiveFrom(Route route, Scanner s) {
        boolean locationParsed = false;

        System.out.println("Введите поля объекта Location через пробел: x(дробное число) y(дробное число). Введите пусую строку, чтобы задать значение null.");
        // Пока координаты не спарсены успешно
        while (!locationParsed){
            try{
                AbstractList<Token> fromTokens = Tokenizer.tokenize(s.nextLine());
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

    public static void receiveTo(Route route, Scanner s) {
        boolean locationParsed = false;

        System.out.println("Введите поля объекта Location через пробел: x(дробное число) y(дробное число). Введите пусую строку, чтобы задать значение null.");
        // Пока координаты не спарсены успешно
        while (!locationParsed){
            try{
                AbstractList<Token> toTokens = Tokenizer.tokenize(s.nextLine());
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
}

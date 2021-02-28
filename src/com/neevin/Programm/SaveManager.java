package com.neevin.Programm;

import com.neevin.DataModels.Route;

import javax.swing.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SaveManager {

    public final String path;

    public SaveManager(String variableName){
        if(variableName == null){
            throw new IllegalArgumentException("Имя переменной окружения не может быть равно null!");
        }
        String path = System.getenv(variableName);

        // Если переменная среды не существует
        if(path == null){
            throw new IndexOutOfBoundsException("Не найдена переменная среды с именем " + variableName);
        }
        this.path = path;
    }

    public void Save(HashMap<Long, Route> collection, Date creationDate) throws FileNotFoundException {
        // Тут подправить нужно
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(
                new FileOutputStream(path)));

        creationDate = new Date();
        encoder.writeObject(creationDate);
        encoder.writeObject(collection);
        encoder.flush();
        encoder.close();

    }

    public CollectionWrapper Load() throws FileNotFoundException {
        XMLDecoder decoder = new XMLDecoder(new FileInputStream(path));
        Date creationDate = (Date) decoder.readObject();
        HashMap<Long,Route> collection = (HashMap<Long, Route>) decoder.readObject();
        decoder.close();

        return new CollectionWrapper(creationDate, collection);
    }

    public void serializeToXML() throws FileNotFoundException {
        HashMap<Integer,TestBean> list = new HashMap<>();

        list.put(1 ,new TestBean("Henry", 42));
        list.put(15,new TestBean("Tom", 11));
        Date d = new Date();

        final XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(
                new FileOutputStream(path)));
        encoder.writeObject(d);
        encoder.writeObject(list);
        encoder.close();

        final XMLDecoder decoder = new XMLDecoder(new FileInputStream(path));
        Date date = (Date) decoder.readObject();
        final HashMap<Integer,TestBean> listFromFile = (HashMap<Integer,TestBean>) decoder.readObject();
        decoder.close();
        System.out.println(date);
        System.out.println("Reading list: " + listFromFile);

    }
}
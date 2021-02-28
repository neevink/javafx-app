package com.neevin.Programm;

import com.neevin.DataModels.Route;

import java.util.Date;
import java.util.HashMap;

/**
 * Класс управляющий коллекцией
 */
public class CollectionController {
                        // id и сама сущность
    public final HashMap<Long, Route> dictionary;

    protected Date initializationTime;
    protected long nextId;

    public CollectionController(HashMap<Long,Route> map, Date initializationTime){
        if (map == null){
            throw new IllegalArgumentException("Аргумент map неможет быть null!");
        }
        if (initializationTime == null){
            throw new IllegalArgumentException("Аргумент initializationTime неможет быть null!");
        }

        this.dictionary = map;
        this.initializationTime = initializationTime;
    }

    public CollectionController(){
        this.dictionary = new HashMap<>();
        this.initializationTime = new Date();
    }



}

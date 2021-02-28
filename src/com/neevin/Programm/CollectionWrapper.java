package com.neevin.Programm;

import com.neevin.DataModels.Route;

import java.util.Date;
import java.util.HashMap;

public class CollectionWrapper {
    public final HashMap<Long, Route> dictionary;
    public final Date creationDate;

    public CollectionWrapper(Date creationDate, HashMap<Long, Route> dictionary) {
        this.dictionary = dictionary;
        this.creationDate = creationDate;
    }
}

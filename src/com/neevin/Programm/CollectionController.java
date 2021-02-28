package com.neevin.Programm;

import com.neevin.DataModels.Route;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.rmi.AccessException;
import java.util.Date;
import java.util.HashMap;

/**
 * Класс управляющий коллекцией
 */
public class CollectionController {
    public HashMap<Long, Route> map;

    public Date initializationTime;
    protected long nextId;
    protected String path;

    public CollectionController(String variableName) throws AccessException {
        if(variableName == null){
            throw new IllegalArgumentException("Имя переменной окружения не может быть равно null!");
        }
        String path = System.getenv(variableName);

        // Если переменная среды не существует
        if(path == null){
            throw new IndexOutOfBoundsException("Не найдена переменная среды с именем " + variableName);
        }
        this.path = path;

        map = new HashMap<>();
        initializationTime = new Date();

        File file = new File(path);

        if(file.exists() && !file.canRead()){
            throw new AccessException("Нет прав на чтение файла с сохранениями. Исправьте и возвращайтесь!");
        }
        if(file.exists() && !file.canWrite()){
            throw new AccessException("Нет прав на запись в файл с сохранениями. Исправьте и возвращайтесь!");
        }

        try {
            Load();
            System.out.println("Файл с сохранениями успешно найден и сохранения загружены.");
        }
        catch (FileNotFoundException e){
            System.out.println("Файл с сохранениями не найден. Файл будет создан при первом сохранении.");
        }
    }

    public void Save() throws IOException {
        XMLEncoder encoder = new XMLEncoder(new FileOutputStream(path));

        encoder.writeObject(this.initializationTime);
        encoder.writeObject(this.map);
        encoder.flush();
        encoder.close();
    }

    public void Load() throws FileNotFoundException {
        XMLDecoder decoder = new XMLDecoder(new FileInputStream(path));

        this.initializationTime = (Date) decoder.readObject();
        this.map = (HashMap<Long, Route>) decoder.readObject();
        decoder.close();
    }

}

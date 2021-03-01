package com.neevin.Programm;

import com.neevin.DataModels.Route;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.beans.*;
import java.io.*;
import java.rmi.AccessException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Класс управляющий коллекцией
 */
public class CollectionController {
    public HashMap<Long, Route> map;

    public Date initializationTime;
    protected long nextId = 1;
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
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void Save() throws IOException {
        String entity = serialize();

        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path));

        writer.write(entity);
        writer.flush();
        writer.close();
    }

    public void Load() throws Exception {
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path));

        ArrayList<Byte> a = new ArrayList<>();
        int c;
        while ((c = inputStream.read()) != -1) {
            byte ch = (byte)c;
            a.add(ch);
        }

        byte[] arr = new byte[a.size()];
        for(int i = 0; i < a.size(); i++) {
            arr[i] = a.get(i);
        }

        deserialize(arr);

        inputStream.close();
    }

    public long getNextId() {
        return this.nextId++;
    }

    private String serialize(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        XMLEncoder encoder = new XMLEncoder(outputStream);

        encoder.writeObject(this.nextId);
        encoder.writeObject(this.initializationTime);
        encoder.writeObject(this.map);
        encoder.flush();
        encoder.close();

        return outputStream.toString();
    }

    private void deserialize(byte[] bytes) throws Exception {
        XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(bytes));

        try{
            this.nextId = (long) decoder.readObject();
            this.initializationTime = (Date) decoder.readObject();
            this.map = (HashMap<Long, Route>) decoder.readObject();
        }
        catch (Exception e){
            throw new Exception("Заргузить данные невозможно, т. к. файл с сохранениями повреждён. Файл c сохраненимя будет создан заново при первом сохранении.");
        }
        decoder.close();
    }
}

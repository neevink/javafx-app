package com.neevin.Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.AccessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class DatabaseConnection {
    Connection dbConnection;

    /**
     * @param configurationFilePath Путь к файлу, в котором хранятся данные для пожключения к базе данных
     */
    public DatabaseConnection(String configurationFilePath) throws Exception{
        File file = new File(configurationFilePath);

        if(file.exists() && !file.canRead()){
            throw new AccessException("Нет прав на чтение файла с данными для подключения к БД. Исправьте и возвращайтесь!");
        }
        if(file.exists() && !file.canWrite()){
            throw new AccessException("Нет прав на запись в файл с данными для подключения к БД. Исправьте и возвращайтесь!");
        }

        try {
            // Чтение файла с настройками
            Scanner input = new Scanner(new FileInputStream(file));
            String jdbcURL = input.nextLine();
            String user = input.nextLine();
            String password = input.nextLine();

            try {
                dbConnection = DriverManager.getConnection(jdbcURL, user, password);
            }
            catch (Exception e){
                throw new AccessException("Не удалось подключиться к базе данных: " + e.getMessage());
            }

            System.out.println("Файл с данными для подключения к БД найден и подключение успешно установлено.");
        }
        catch (FileNotFoundException e){
            System.out.println("Файл с сохранениями не найден. Файл будет создан при первом сохранении.");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

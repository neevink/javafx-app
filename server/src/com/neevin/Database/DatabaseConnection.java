package com.neevin.Database;

import com.neevin.DataModels.*;
import com.neevin.Net.CommandResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.AccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DatabaseConnection {
    Connection dbConnection;
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * @param configurationFilePath Путь к файлу, в котором хранятся данные для пожключения к базе данных
     */
    public DatabaseConnection(String configurationFilePath) throws Exception{
        File file = new File(configurationFilePath);

        if(file.exists() && !file.canRead()){
            throw new AccessException("Нет прав на чтение файла с данными для подключения к БД. Исправьте и возвращайтесь!");
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
        }
        catch (FileNotFoundException e){
            throw new FileNotFoundException("Файл с сохранениями не найден.");
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public boolean registerAccount(String login, String passwordHash, String salt) throws SQLException {
        final String q = "INSERT INTO \"Users\" (\"Login\", \"Password\", \"Salt\") VALUES (?, ?, ?);";

        readWriteLock.writeLock().lock();
        try {
            PreparedStatement registerStatement = dbConnection.prepareStatement(q);
            registerStatement.setString(1, login);
            registerStatement.setString(2, passwordHash);
            registerStatement.setString(3, salt);
            int res = registerStatement.executeUpdate();
            registerStatement.close();

            return res == 1;
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void checkAccountPasswordHash(String login, String passwordHash) throws SQLException {
        final String q = "SELECT count(*) FROM \"Users\" WHERE \"Login\" = ? AND \"Password\" = ?;";

        readWriteLock.writeLock().lock();
        try {
            PreparedStatement checkPwdStatement = dbConnection.prepareStatement(q);
            checkPwdStatement.setString(1, login);
            checkPwdStatement.setString(2, passwordHash);
            ResultSet res = checkPwdStatement.executeQuery();
            if (res.next()){
                int ans = res.getInt(1);
                checkPwdStatement.close();
                if(ans != 1){
                    throw new SQLException("Неверный логин/пароль");
                }
            }
            else{
                throw new SQLException("Неверный логин/пароль");
            }
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public String getAccountSalt(String login) throws SQLException {
        final String q = "SELECT \"Salt\" FROM \"Users\" WHERE \"Login\" = ?;";

        readWriteLock.writeLock().lock();
        try {
            PreparedStatement getSaltStatement = dbConnection.prepareStatement(q);
            getSaltStatement.setString(1, login);
            ResultSet res = getSaltStatement.executeQuery();

            if (res.next()) {
                String ans = res.getString(1);
                getSaltStatement.close();
                return ans;
            } else {
                throw new SQLException("Нет аккаунта с таким логином!");
            }
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public ArrayList<Route> getAllRoutes() throws Exception {
        final String q = "SELECT * FROM \"Routes\";";

        ArrayList<Route> arr = new ArrayList<>();
        try {
            readWriteLock.writeLock().lock();

            PreparedStatement getStatement = dbConnection.prepareStatement(q);
            ResultSet result = getStatement.executeQuery();

            while (result.next()) {
                try {
                    Route newRoute = new Route();

                    newRoute.setId(result.getLong(1));
                    newRoute.setName(result.getString(3));

                    Coordinates coord = new Coordinates();
                    coord.setX(result.getDouble(4));
                    coord.setY(result.getDouble(5));

                    newRoute.setCoordinates(coord);
                    newRoute.setCreationDate(result.getTimestamp(6));

                    Location loc = new Location();
                    loc.setX(result.getDouble(7));
                    loc.setY(result.getDouble(8));
                    loc.setName(result.getString(9));

                    newRoute.setFrom(loc);
                    LocationInteger locInt = new LocationInteger();
                    locInt.setX(result.getInt(10));
                    locInt.setY(result.getInt(11));
                    locInt.setName(result.getString(12));

                    newRoute.setTo(locInt);
                    newRoute.setDistance(result.getLong(13));

                    arr.add(newRoute);
                } catch (Exception exc) {
                    throw new Exception("Невозможно загрузить данные из базы: они повреждены");
                }
            }
            getStatement.close();
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
        return arr;
    }

    public Route getRouteById(Long routeId) throws Exception {
        final String q = "SELECT * FROM \"Routes\" WHERE \"Id\" = ?;";

        readWriteLock.writeLock().lock();
        try {
            PreparedStatement getStatement = dbConnection.prepareStatement(q);
            getStatement.setLong(1, routeId);
            ResultSet result = getStatement.executeQuery();

            if(result.next()){
                try{
                    Route newRoute = new Route();

                    newRoute.setId(result.getLong(1));
                    newRoute.setName(result.getString(3));

                    Coordinates coord = new Coordinates();
                    coord.setX(result.getDouble(4));
                    coord.setY(result.getDouble(5));

                    newRoute.setCoordinates(coord);
                    newRoute.setCreationDate(result.getTimestamp(6));

                    Location loc = new Location();
                    loc.setX(result.getDouble(7));
                    loc.setY(result.getDouble(8));
                    loc.setName(result.getString(9));

                    newRoute.setFrom(loc);
                    LocationInteger locInt = new LocationInteger();
                    locInt.setX(result.getInt(10));
                    locInt.setY(result.getInt(11));
                    locInt.setName(result.getString(12));

                    newRoute.setTo(locInt);
                    newRoute.setDistance(result.getLong(13));

                    return newRoute;
                }
                catch (Exception exc){
                    throw new Exception("Невозможно загрузить данные из базы: они повреждены");
                }
            }
            getStatement.close();
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
        throw new Exception("Маршрута с таким идентификатором не сеществует");
    }

    public void AddRoute(Route r, String ownerName) throws SQLException {
        final String q = "INSERT INTO \"Routes\" (\"Owner\", \"Name\", \"CoordinateX\", \"CoordinateY\", \"Date\", \"FromX\", \"FromY\", \"FromName\", \"ToX\", \"ToY\", \"ToName\", \"Distance\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        readWriteLock.writeLock().lock();
        try {
            PreparedStatement addStatement = dbConnection.prepareStatement(q);
            addStatement.setString(1, ownerName);
            addStatement.setString(2, r.getName());
            addStatement.setDouble(3, r.getCoordinates().getX());
            addStatement.setDouble(4, r.getCoordinates().getY());

            addStatement.setTimestamp(5, new Timestamp(r.getCreationDate().getTime()));

            addStatement.setDouble(6, r.getFrom().getX());
            addStatement.setDouble(7, r.getFrom().getY());
            addStatement.setString(8, r.getFrom().getName());
            addStatement.setInt(9, r.getTo().getX());
            addStatement.setInt(10, r.getTo().getY());
            addStatement.setString(11, r.getTo().getName());
            addStatement.setLong(12, r.getDistance());

            addStatement.executeUpdate();
            addStatement.close();
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void deleteAllRoutes(String login) throws SQLException {
        final String q = "DELETE FROM \"Routes\" WHERE \"Owner\" = ?;";

        readWriteLock.writeLock().lock();
        try {
            PreparedStatement deleteStatement = dbConnection.prepareStatement(q);
            deleteStatement.setString(1, login);
            deleteStatement.executeUpdate();
            deleteStatement.close();
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void deleteRoute(String login, long routeId) throws SQLException {
        final String q = "DELETE FROM \"Routes\" WHERE \"Owner\" = ? AND \"Id\" = ?;";

        readWriteLock.writeLock().lock();
        try {
            PreparedStatement deleteStatement = dbConnection.prepareStatement(q);
            deleteStatement.setString(1, login);
            deleteStatement.setLong(2, routeId);
            int rows = deleteStatement.executeUpdate();
            deleteStatement.close();

            if (rows != 1) {
                throw new SQLException(String.format("Маршрут с индентификаторм %d, не пренадлежит данному пользователю или не существует", routeId));
            }
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public boolean isOwnedByUser(String login, long routeId) {
        final String q = "SELECT count(*) FROM \"Routes\" WHERE \"Owner\" = ? AND \"Id\" = ?;";
        int rows = 0;
        readWriteLock.writeLock().lock();
        try {
            PreparedStatement countStatement = dbConnection.prepareStatement(q);
            countStatement.setString(1, login);
            countStatement.setLong(2, routeId);
            ResultSet rs = countStatement.executeQuery();
            rs.next();
            rows = rs.getInt(1);
            countStatement.close();
        }
        catch (Exception exc) {}
        finally {
            readWriteLock.writeLock().unlock();
        }

        return rows != 0;
    }

    public void updateRoute(String ownerName, Route r) {
        final String q = "UPDATE \"Routes\" SET \"Name\" = ?, \"CoordinateX\" = ?, \"CoordinateY\" = ?, \"FromX\" = ?, \"FromY\" = ?, \"FromName\" = ?, \"ToX\" = ?, \"ToY\" = ?, \"ToName\" = ?, \"Distance\" = ? WHERE \"Owner\" = ? AND \"Id\" = ?;";

        readWriteLock.writeLock().lock();
        try {
            PreparedStatement addStatement = dbConnection.prepareStatement(q);
            addStatement.setString(1, r.getName());
            addStatement.setDouble(2, r.getCoordinates().getX());
            addStatement.setDouble(3, r.getCoordinates().getY());

            addStatement.setDouble(4, r.getFrom().getX());
            addStatement.setDouble(5, r.getFrom().getY());
            addStatement.setString(6, r.getFrom().getName());
            addStatement.setInt(7, r.getTo().getX());
            addStatement.setInt(8, r.getTo().getY());
            addStatement.setString(9, r.getTo().getName());
            addStatement.setLong(10, r.getDistance());

            addStatement.setString(11, ownerName);
            addStatement.setLong(12, r.getId());

            addStatement.executeUpdate();
            addStatement.close();
        }
        catch (Exception exc) {}
        finally {
            readWriteLock.writeLock().unlock();
        }
    }
}

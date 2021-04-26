package com.neevin.Database;

import com.neevin.DataModels.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Выпилить
public class DatabaseQueryFactory {
    protected Connection connection;
    DatabaseQueryFactory(Connection connection){
        this.connection = connection;
    }

    public boolean registerAccount(Account account, String salt) throws SQLException {
        final String q = "INSERT INTO Users (Login, Password, Salt) VALUES (?, ?, ?);";

        PreparedStatement registerStatement = connection.prepareStatement(q);
        registerStatement.setString(1, account.login);
        registerStatement.setString(2, account.password);
        registerStatement.setString(3, salt);
        int res = registerStatement.executeUpdate();
        registerStatement.close();

        return res == 1;
    }
}

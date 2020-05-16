package com.urice.webapp.sql;

import com.urice.webapp.exception.ExistStorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sqlString, ElementExecutor<T> elementExecutor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlString)) {
            return elementExecutor.executor(ps);
        } catch (SQLException e) {
            throw new ExistStorageException(e.getSQLState());
        }
    }
}

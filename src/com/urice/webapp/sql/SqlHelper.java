package com.urice.webapp.sql;

import com.urice.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sql) {
        execute(sql, PreparedStatement::execute);
    }

    public <T> T execute(String sqlString, SqlExecutor<T> sqlExecutor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlString)) {
            return sqlExecutor.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> sqlExecutor){
        try (Connection connection = connectionFactory.getConnection()){
           try {
               connection.setAutoCommit(false);
               T res = sqlExecutor.execute(connection);
               connection.commit();
               return res;
           } catch (SQLException e) {
               connection.rollback();
               throw ExceptionUtil.convertException(e);
           }
        } catch (SQLException e) {
          throw new StorageException(e);
        }
    }
}

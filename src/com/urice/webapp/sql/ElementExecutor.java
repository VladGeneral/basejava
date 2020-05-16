package com.urice.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ElementExecutor<T> {
    T executor(PreparedStatement preparedStatement) throws SQLException;
}

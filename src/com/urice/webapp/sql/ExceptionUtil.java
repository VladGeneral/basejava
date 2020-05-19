package com.urice.webapp.sql;

import com.urice.webapp.exception.ExistStorageException;
import com.urice.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
            if (e.getSQLState().equals("23505")) {
//                http://www.postgresql.org/docs/9.3/static/errcodes-appendix.html
                return new ExistStorageException(null);
            }
        }
        return new StorageException(e);
    }
}

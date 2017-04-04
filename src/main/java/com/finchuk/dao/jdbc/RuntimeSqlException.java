package com.finchuk.dao.jdbc;

/**
 * Created by olexandr on 28.03.17.
 */
public class RuntimeSqlException extends RuntimeException {

    public RuntimeSqlException(Throwable cause) {
        super(cause);
    }

    public RuntimeSqlException(String message) {
        super(message);
    }

    public RuntimeSqlException(String message, Throwable cause) {
        super(message, cause);
    }
}

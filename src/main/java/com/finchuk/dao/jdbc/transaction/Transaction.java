package com.finchuk.dao.jdbc.transaction;

import com.finchuk.dao.jdbc.ConnectionManager;
import com.finchuk.dao.jdbc.RuntimeSqlException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by olexandr on 26.03.17.
 */
@FunctionalInterface
public interface Transaction {
    Logger LOGGER = LogManager.getLogger(Transaction.class);

    void pass();

    static void doTransaction(Transaction tx, int transactionIsolationLevel) {
        ConnectionManager cm = ConnectionManager.getInstance();
        Connection connection = cm.getConnection();
        try {
            boolean isAutoCommited = connection.getAutoCommit();
            connection.setAutoCommit(false);
            int oldIsolation = connection.getTransactionIsolation();
            if (oldIsolation != transactionIsolationLevel) {
                connection.setTransactionIsolation(transactionIsolationLevel);
            }
            tx.pass();
            if (isAutoCommited) {
                connection.commit();
                connection.setAutoCommit(isAutoCommited);
            }
            if (connection.getTransactionIsolation() != oldIsolation) {
                connection.setTransactionIsolation(oldIsolation);
            }

        } catch (SQLException | RuntimeSqlException e) {
            LOGGER.error("transaction failed", e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.error("can't rollback", e1);
            }

            throw new RuntimeSqlException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("can't close", e);
            }
        }
    }

    static void doTransaction(Transaction tx) {
        doTransaction(tx, Connection.TRANSACTION_READ_COMMITTED);
    }
}

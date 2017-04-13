package com.finchuk.dao.jdbc;

import com.finchuk.dao.jdbc.proxies.DataSourceProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * ConnectionManager encapsulates a data source. The static methods
 * allow initiate ConnectionManager from various sources
 */
public class ConnectionManager {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionManager.class);

    private static ConnectionManager connectionManager;
    DataSource dataSource;

    private ConnectionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.warn("Can't create connection", e);
        }
        return null;
    }

    public static ConnectionManager getInstance() {
        if (connectionManager == null) {
            throw new IllegalStateException("Connection manager hasn't initialized yet!");
        }
        return connectionManager;
    }

    public static synchronized void initFromJNDI(String name) {
        if (connectionManager != null) {
            return;
        }

        try {
            Context initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup(name);
            DataSourceProxy dspx = new DataSourceProxy(ds);
            connectionManager = new ConnectionManager(dspx);
        } catch (NamingException e) {
            LOGGER.error("Can't create initial context", e);
        }
    }

    public synchronized static void initFromDS(DataSource ds) {
        if (connectionManager != null) {
            return;
        }
        connectionManager = new ConnectionManager(new DataSourceProxy(ds));
    }

    public void clean() {
        if (dataSource instanceof DataSourceProxy) {
            DataSourceProxy txDs = (DataSourceProxy) dataSource;
            txDs.releaseConnection();
        }
    }
}

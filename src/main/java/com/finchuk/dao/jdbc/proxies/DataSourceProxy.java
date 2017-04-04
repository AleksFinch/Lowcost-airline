package com.finchuk.dao.jdbc.proxies;

import org.apache.logging.log4j.LogManager;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Created by olexandr on 26.03.17.
 */
public class DataSourceProxy implements DataSource {
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(DataSourceProxy.class);

    private DataSource dataSource;
    private ThreadLocal<ConnectionProxy> connectionLocal = new ThreadLocal<>();

    public DataSourceProxy(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void releaseConnection() {
        ConnectionProxy connPx = connectionLocal.get();
        if (connPx != null) {
            try {
                connPx.getConnection().close();
            } catch (SQLException e) {
                LOGGER.error("Can't close connection", e);
            } finally {
                connectionLocal.remove();
            }
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        ConnectionProxy connPx = connectionLocal.get();
        if (connPx == null) {
            Connection newConn = dataSource.getConnection();
            connPx = new ConnectionProxy(newConn, this);
            connectionLocal.set(connPx);
        }
        connPx.markBusy();
        return connPx;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return dataSource.getLoginTimeout();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return dataSource.getLogWriter();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return dataSource.getParentLogger();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        dataSource.setLoginTimeout(seconds);
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        dataSource.setLogWriter(out);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return dataSource.isWrapperFor(iface);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return dataSource.unwrap(iface);
    }
}

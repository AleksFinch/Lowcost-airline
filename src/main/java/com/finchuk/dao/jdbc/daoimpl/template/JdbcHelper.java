package com.finchuk.dao.jdbc.daoimpl.template;

import com.finchuk.dao.jdbc.ConnectionManager;
import com.finchuk.dao.jdbc.RuntimeSqlException;
import com.finchuk.dao.jdbc.mappers.EntityMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulate a lot of boilerplate codes for JDBC
 * It also contains error handling and logging code
 */
public class JdbcHelper {
    private static final Logger LOGGER = LogManager.getLogger(JdbcHelper.class);

    private ConnectionManager cm;

    public JdbcHelper(ConnectionManager cm) {
        this.cm = cm;
    }

    public Long insert(String query, Object... params) {
        Connection connection = cm.getConnection();
        if (connection == null) {
            return null;
        }
        try (PreparedStatement statement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            LOGGER.error("Can't insert into DB");
            throw new RuntimeSqlException(e);
        } finally {
            closeConn(connection);
        }
        return null;
    }

    private void executeQuery(String query, ResultSetWorker worker, Object... params) {
        Connection connection = cm.getConnection();
        if (connection == null) {
            return;
        }
        try (PreparedStatement statement
                     = connection.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            ResultSet rs = statement.executeQuery();
            worker.execute(rs);

        } catch (SQLException e) {
            LOGGER.error("Can't get object DB");
            throw new RuntimeSqlException(e);
        } finally {
            closeConn(connection);
        }
    }

    public <T> T findObject(String query, EntityMapper<T> mapper, Object... params) {
        Object[] result = new Object[1];
        executeQuery(query, (rs) -> {
            if (rs.next()) {
                result[0] = mapper.map(rs);
            }
        }, params);
        return (T) result[0];
    }

    public <T> List<T> findObjects(String query, EntityMapper<T> mapper, Object... params) {
        List<T> result = new ArrayList<>();
        executeQuery(query, (rs) -> {
            while (rs.next()) {
                result.add(mapper.map(rs));
            }
        }, params);
        return result;
    }

    public int update(String query, Object... params) {
        Connection conn = cm.getConnection();

        if (conn == null)
            return 0;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot execute update query", e);
            throw new RuntimeSqlException(e);
        } finally {
            closeConn(conn);
        }
    }

    public int remove(String query, Object... params) {
        return update(query, params);
    }

    private void closeConn(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Cannot close jdbc connection", e);
        }
    }

    public void executeScripts(String path) {
        File file = new File(path);
        if (!file.exists()) {
            LOGGER.error("file" + path + "doesn't exist");
            throw new IllegalArgumentException();
        }
        try {
            String script = new String(Files.readAllBytes(file.toPath()));
            script = script.replaceAll("[\\s]+", " ");
            String[] queries = script.split(";");
            Connection connection = cm.getConnection();

            try {
                for (String query :
                        queries) {
                    if (query.trim().isEmpty()) {
                        continue;
                    }
                    Statement statement = connection.createStatement();
                    statement.execute(query);
                }
            } catch (SQLException e) {
                LOGGER.error("can't execute scripts", e);
                throw new IllegalArgumentException(e);
            } finally {
                closeConn(connection);
            }
        } catch (IOException e) {
            LOGGER.error("can't read" + path, e);
            throw new IllegalArgumentException(e);
        }

    }

    @FunctionalInterface
    private interface ResultSetWorker {
        void execute(ResultSet rs) throws SQLException;
    }
}

package testdb;

import com.finchuk.dao.jdbc.ConnectionManager;
import com.finchuk.dao.jdbc.daoimpl.template.JdbcHelper;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DBInitializer {
    private static boolean initialized = false;

    public static void initH2() {
        if (initialized) {
            return;
        }
        DataSource ds = getH2DS();
        ConnectionManager.initFromDS(ds);
        JdbcHelper helper = new JdbcHelper(ConnectionManager.getInstance());
        helper.executeScripts("./src/main/resources/create_db.sql");
        initialized = true;
    }

    public static void initMySql() {
        if (initialized) {
            return;
        }
        DataSource ds = getMySqlDS();
        ConnectionManager.initFromDS(ds);
        JdbcHelper helper = new JdbcHelper(ConnectionManager.getInstance());
        helper.executeScripts("./src/main/resources/create_db.sql");
        initialized = true;
    }

    private static DataSource getH2DS() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setUrl("jdbc:h2:mem:test;Mode=MYSQL;DB_CLOSE_DELAY=-1");
        ds.setUser("fin");
        ds.setPassword("password");
        return ds;
    }

    private static DataSource getMySqlDS() {
        Properties properties = new Properties();
        MysqlDataSource ds = new MysqlDataSource();
        try {
            FileInputStream fis = new FileInputStream("./src/main/resources/database.properties");
            properties.load(fis);
            ds.setURL(properties.getProperty("jdbc.url"));
            ds.setUser(properties.getProperty("jdbc.user"));
            ds.setPassword(properties.getProperty("jdbc.password"));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return ds;
    }

}

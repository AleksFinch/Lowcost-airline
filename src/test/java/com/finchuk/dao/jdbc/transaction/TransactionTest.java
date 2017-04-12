package com.finchuk.dao.jdbc.transaction;

import com.finchuk.dao.AirlineDao;
import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.dao.jdbc.RuntimeSqlException;
import com.finchuk.dto.Airline;
import creator.EntityCreator;
import org.junit.BeforeClass;
import org.junit.Test;
import testdb.DBInitializer;

import static org.junit.Assert.assertEquals;

/**
 * Created by root on 12.04.17.
 */
public class TransactionTest {
    @BeforeClass
    public static void init() {
        DBInitializer.initMySql();
    }

    AirlineDao dao = JdbcDaoFactory.getInstance().getAirlineDao();

    @Test
    public void testNestedTransactionOk() {
        Airline airline = EntityCreator.createAirline();
        Transaction.doTransaction(() -> {

            Long id = dao.add(airline);
            airline.setCompanyId(id);

            Transaction.doTransaction(() -> {
                airline.setCompanyName("FlyToSky");
                dao.update(airline);
            });
        });

        Airline airline1 = dao.find(airline.getCompanyId());
        dao.delete(airline.getCompanyId());
        assertEquals(airline, airline1);


    }

    @Test
    public void testNestedTransactionWhenNestedFails() {
        Airline airline = EntityCreator.createAirline();
        try {
            Transaction.doTransaction(() -> {

                Long id = dao.add(airline);
                airline.setCompanyId(id);

                Transaction.doTransaction(() -> {
                    airline.setCompanyName(null);
                    dao.update(airline);
                });
            });
        } catch (RuntimeSqlException e) {
        }

        assertEquals(null, dao.find(airline.getCompanyId()));
    }


    @Test
    public void testNestedTransactionWhenOuterFails() {
        Airline airline = EntityCreator.createAirline();
        airline.setCompanyName(null);
        try {
            Transaction.doTransaction(() -> {

                Long id = dao.add(airline);
                airline.setCompanyId(id);

                Transaction.doTransaction(() -> {
                    airline.setCompanyName("SkyToFly");
                    Long newId = dao.add(airline);
                    airline.setCompanyId(newId);
                });
            });
        } catch (RuntimeSqlException e) {
        }


        assertEquals(null, dao.find(airline.getCompanyId()));
    }
}
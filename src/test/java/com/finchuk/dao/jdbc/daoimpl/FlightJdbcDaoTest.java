package com.finchuk.dao.jdbc.daoimpl;

import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.entities.Flight;
import creator.EntityCreator;

import java.math.BigDecimal;

/**
 * Created by olexandr on 28.03.17.
 */
public class FlightJdbcDaoTest extends AbstractJdbcDaoTest<Flight, Long> {

    public FlightJdbcDaoTest() {
        dao = JdbcDaoFactory.getInstance().getFlightDao();

    }

    @Override
    protected Long getId(Flight obj) {
        return obj.getFlightId();
    }

    @Override
    protected void setId(Flight obj, Long id) {
        obj.setFlightId(id);
    }

    @Override
    protected Flight getTestObj() {
        return EntityCreator.createFlight();
    }

    @Override
    protected void updateObj(Flight obj) {
        obj.setStartPrice(BigDecimal.valueOf(2560000));
    }


}
package com.finchuk.dao.jdbc.daoimpl;

import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.dto.Airport;
import creator.EntityCreator;

/**
 * Created by olexandr on 28.03.17.
 */
public class AirportJdbcDaoTest extends AbstractJdbcDaoTest<Airport, String> {

    public AirportJdbcDaoTest() {
        dao = JdbcDaoFactory.getInstance().getAirportDao();

    }

    @Override
    protected String getId(Airport obj) {
        return obj.getAirportId();
    }

    @Override
    protected void setId(Airport obj, String id) {
        obj.setAirportId(id);
    }

    @Override
    protected Airport getTestObj() {

        return EntityCreator.createAirport();
    }

    @Override
    protected void updateObj(Airport obj) {

        obj.setTown("BerezneCity");
    }


}
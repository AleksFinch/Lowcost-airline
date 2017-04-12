package com.finchuk.dao.jdbc.daoimpl;

import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.dto.Airline;
import creator.EntityCreator;

/**
 * Created by olexandr on 28.03.17.
 */
public class AirlineJdbcDaoTest extends AbstractJdbcDaoTest<Airline, Long> {

    public AirlineJdbcDaoTest() {
        dao = JdbcDaoFactory.getInstance().getAirlineDao();

    }

    @Override
    protected Long getId(Airline obj) {
        return obj.getCompanyId();
    }

    @Override
    protected void setId(Airline obj, Long id) {
        obj.setCompanyId(id);
    }

    @Override
    protected Airline getTestObj() {
        return EntityCreator.createAirline();
    }

    @Override
    protected void updateObj(Airline obj) {
        obj.setCompanyName("PedashAir");
    }


}
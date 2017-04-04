package com.finchuk.services.impl;

import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.entities.Airline;
import com.finchuk.services.AbstractEntityService;

/**
 * Created by olexandr on 29.03.17.
 */
public class AirlineService extends AbstractEntityService<Airline, Long> {
    private static AirlineService airlineService = new AirlineService();

    private AirlineService() {
        dao = JdbcDaoFactory.getInstance().getAirlineDao();
    }

    @Override
    protected void setId(Airline obj, Long id) {
        obj.setCompanyId(id);
    }

    public static AirlineService getInstance() {
        return airlineService;
    }
}

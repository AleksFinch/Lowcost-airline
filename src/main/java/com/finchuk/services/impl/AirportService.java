package com.finchuk.services.impl;

import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.dto.Airport;
import com.finchuk.services.AbstractEntityService;

/**
 * Created by olexandr on 29.03.17.
 */
public class AirportService extends AbstractEntityService<Airport, String> {
    public AirportService() {
        dao = JdbcDaoFactory.getInstance().getAirportDao();
    }

    @Override
    protected void setId(Airport obj, String id) {
        obj.setAirportId(id);
    }

    @Override
    public synchronized boolean add(Airport airport) {
        if (find(airport.getAirportId()) == null) {
            airport.setAirportId(airport.getAirportId().toLowerCase());
            dao.add(airport);
            return true;
        }
        return false;

    }

}

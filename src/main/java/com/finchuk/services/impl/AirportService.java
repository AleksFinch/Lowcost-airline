package com.finchuk.services.impl;

import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.entities.Airport;
import com.finchuk.services.AbstractEntityService;

/**
 * Created by olexandr on 29.03.17.
 */
public class AirportService extends AbstractEntityService<Airport, String> {
    private static AirportService airportService = new AirportService();

    private AirportService() {
        dao = JdbcDaoFactory.getInstance().getAirportDao();
    }

    @Override
    protected void setId(Airport obj, String id) {
        obj.setAirportId(id);
    }

    @Override
    public boolean add(Airport airport) {
        if(find(airport.getAirportId())==null){
            super.add(airport);
            return true;
        }
        return false;

    }

    public static AirportService getInstance() {
        return airportService;
    }
}

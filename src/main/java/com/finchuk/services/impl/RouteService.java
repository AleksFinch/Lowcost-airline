package com.finchuk.services.impl;

import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.entities.Route;
import com.finchuk.services.AbstractEntityService;

/**
 * Created by olexandr on 29.03.17.
 */
public class RouteService extends AbstractEntityService<Route, Long> {
    private static RouteService routeService = new RouteService();

    private AirlineService airlineService = AirlineService.getInstance();
    private AirportService airportService = AirportService.getInstance();

    private RouteService() {
        dao = JdbcDaoFactory.getInstance().getRouteDao();
    }

    @Override
    protected void setId(Route obj, Long id) {
        obj.setRouteId(id);
    }

    @Override
    protected void loadTails(Route obj) {
        obj.setAirportFrom(airportService.find(obj.getAirportFrom().getAirportId()));
        obj.setAirportTo(airportService.find(obj.getAirportTo().getAirportId()));
        obj.setCompany(airlineService.find(obj.getCompany().getCompanyId()));
    }

    public static RouteService getInstance() {
        return routeService;
    }
}

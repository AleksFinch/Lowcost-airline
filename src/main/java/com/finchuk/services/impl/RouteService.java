package com.finchuk.services.impl;

import com.finchuk.dao.RouteDao;
import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.dto.Route;
import com.finchuk.services.AbstractEntityService;
import com.finchuk.services.factory.ServiceFactory;

/**
 * Created by olexandr on 29.03.17.
 */
public class RouteService extends AbstractEntityService<Route, Long> {

    private AirlineService airlineService;
    private AirportService airportService;

    public RouteService() {
        dao = JdbcDaoFactory.getInstance().getRouteDao();
    }

    public void init(){
        airlineService = ServiceFactory.getAirlineService();
        airportService = ServiceFactory.getAirportService();
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

    public synchronized Route loadOrCreate(Route r){
        Route route = ((RouteDao)dao).findWhole(r);
        if(route == null){
            add(r);
            route = r;
        }
        return route;
    }


}

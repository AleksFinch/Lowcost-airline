package com.finchuk.dao.factory;

import com.finchuk.dao.*;

/**
 * Created by olexandr on 25.03.17.
 */
public interface DaoFactory {
    AirlineDao getAirlineDao();

    AirportDao getAirportDao();

    FlightDao getFlightDao();

    RouteDao getRouteDao();

    TicketDao getTicketDao();

    UserDao getUserDao();
}

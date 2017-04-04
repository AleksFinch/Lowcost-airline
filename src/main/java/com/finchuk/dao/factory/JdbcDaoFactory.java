package com.finchuk.dao.factory;

import com.finchuk.dao.*;
import com.finchuk.dao.jdbc.daoimpl.*;

/**
 * Created by olexandr on 25.03.17.
 */
public class JdbcDaoFactory implements DaoFactory {
    private static JdbcDaoFactory factory = new JdbcDaoFactory();

    private AirlineJdbcDao airlineJdbcDao = new AirlineJdbcDao();
    private AirportJdbcDao airportJdbcDao = new AirportJdbcDao();
    private FlightJdbcDao flightJdbcDao = new FlightJdbcDao();
    private RouteJdbcDao routeJdbcDao = new RouteJdbcDao();
    private TicketJdbcDao ticketJdbcDao = new TicketJdbcDao();
    private UserJdbcDao userJdbcDao = new UserJdbcDao();

    private JdbcDaoFactory() {

    }

    @Override
    public AirlineDao getAirlineDao() {
        return airlineJdbcDao;
    }

    @Override
    public AirportDao getAirportDao() {
        return airportJdbcDao;
    }

    @Override
    public FlightDao getFlightDao() {
        return flightJdbcDao;
    }

    @Override
    public RouteDao getRouteDao() {
        return routeJdbcDao;
    }

    @Override
    public TicketDao getTicketDao() {
        return ticketJdbcDao;
    }

    @Override
    public UserDao getUserDao() {
        return userJdbcDao;
    }

    public static JdbcDaoFactory getInstance() {
        return factory;
    }
}

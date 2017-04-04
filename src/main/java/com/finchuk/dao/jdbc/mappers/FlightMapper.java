package com.finchuk.dao.jdbc.mappers;

import com.finchuk.entities.Flight;
import com.finchuk.entities.Route;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by olexandr on 25.03.17.
 */
public class FlightMapper {
    private static final Logger LOGGER = LogManager.getLogger(FlightMapper.class);

    public static Flight map(ResultSet resultSet) {
        Flight flight = new Flight();
        try {
            flight.setFlightId(resultSet.getLong("id"));
            flight.setDepartureTime(resultSet.getTimestamp("departure_time").toLocalDateTime());
            Route route = new Route();
            route.setRouteId(resultSet.getLong("route"));
            flight.setRoute(route);
            flight.setStartPrice(resultSet.getBigDecimal("start_price"));
            flight.setStartPriceForBusiness(resultSet.getBigDecimal("start_price_business"));
        } catch (SQLException e) {
            //TODO throw runtime exception
            LOGGER.error("can't map object");
        }
        return flight;
    }
}

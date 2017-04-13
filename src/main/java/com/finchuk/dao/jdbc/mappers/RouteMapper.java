package com.finchuk.dao.jdbc.mappers;

import com.finchuk.dto.Airline;
import com.finchuk.dto.Airport;
import com.finchuk.dto.Route;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by olexandr on 25.03.17.
 */
public class RouteMapper {
    private static final Logger LOGGER = LogManager.getLogger(RouteMapper.class);

    public static Route map(ResultSet resultSet) {

        Route route = new Route();
        try {
            route.setRouteId(resultSet.getLong("id"));
            route.setFlightDuration(resultSet.getTime("flight_duration").toLocalTime());
            route.setPlane(resultSet.getString("plane"));
            Airport from = new Airport();
            from.setAirportId(resultSet.getString("airport_from"));
            route.setAirportFrom(from);
            Airport to = new Airport();
            to.setAirportId(resultSet.getString("airport_to"));
            route.setAirportTo(to);
            Airline airline = new Airline();
            airline.setCompanyId(resultSet.getLong("company"));
            route.setCompany(airline);
        } catch (SQLException e) {
            LOGGER.error("can't map object", e);
        }
        return route;
    }
}

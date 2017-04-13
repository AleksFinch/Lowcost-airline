package com.finchuk.dao.jdbc.mappers;

import com.finchuk.dto.Airport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by olexandr on 25.03.17.
 */
public class AirportMapper {
    private static final Logger LOGGER = LogManager.getLogger(AirportMapper.class);

    public static Airport map(ResultSet resultSet) {
        Airport airport = new Airport();
        try {
            airport.setAirportId(resultSet.getString("id"));
            airport.setCountry(resultSet.getString("country"));
            airport.setTown(resultSet.getString("town"));
        } catch (SQLException e) {
            LOGGER.error("can't map object");
        }

        return airport;
    }
}

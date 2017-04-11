package com.finchuk.dao.jdbc.mappers;

import com.finchuk.entities.Airline;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by olexandr on 25.03.17.
 */
public class AirlineMapper {
    private static final Logger LOGGER = LogManager.getLogger(AirlineMapper.class);

    public static Airline map(ResultSet resultSet) {

        Airline airline = new Airline();
        try {
            airline.setCompanyId(resultSet.getLong("id"));
            airline.setCompanyName(resultSet.getString("company_name"));
            airline.setImgPath(Optional
                    .ofNullable(resultSet.getString("img_path"))
                    .map((e) -> {
                        try {
                            return new URI(e);
                        } catch (URISyntaxException e1) {
                            LogManager.getLogger().error("invalid URI");
                            return null;
                        }
                    })
                    .orElse(null));
        } catch (SQLException e) {
            LOGGER.error("can't map object", e);
        }
        return airline;
    }
}

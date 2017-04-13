package com.finchuk.dao.jdbc.mappers;

import com.finchuk.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by olexandr on 25.03.17.
 */
public class UserMapper {
    private static final Logger LOGGER = LogManager.getLogger(UserMapper.class);

    public static User map(ResultSet resultSet) {

        User user = new User();
        try {
            user.setUserId(resultSet.getLong("id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.seteMail(resultSet.getString("e_mail"));
            user.setPassword(resultSet.getString("password"));

        } catch (SQLException e) {
            LOGGER.error("can't map object", e);
        }
        return user;
    }
}

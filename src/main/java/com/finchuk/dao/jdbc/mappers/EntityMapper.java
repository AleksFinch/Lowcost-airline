package com.finchuk.dao.jdbc.mappers;

import java.sql.ResultSet;

/**
 * Created by olexandr on 27.03.17.
 */
@FunctionalInterface
public interface EntityMapper<T> {
    T map(ResultSet rs);
}

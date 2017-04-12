package com.finchuk.dao.jdbc.daoimpl;

import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.dto.Route;
import creator.EntityCreator;

/**
 * Created by olexandr on 28.03.17.
 */
public class RouteJdbcDaoTest extends AbstractJdbcDaoTest<Route, Long> {

    public RouteJdbcDaoTest() {
        dao = JdbcDaoFactory.getInstance().getRouteDao();

    }

    @Override
    protected Long getId(Route obj) {
        return obj.getRouteId();
    }

    @Override
    protected void setId(Route obj, Long id) {
        obj.setRouteId(id);
    }

    @Override
    protected Route getTestObj() {
        return EntityCreator.createRoute();
    }

    @Override
    protected void updateObj(Route obj) {
        obj.setPlane("Kukuruznik");
    }


}
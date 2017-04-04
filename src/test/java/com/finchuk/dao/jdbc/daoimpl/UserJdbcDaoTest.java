package com.finchuk.dao.jdbc.daoimpl;

import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.entities.User;
import creator.EntityCreator;

/**
 * Created by olexandr on 28.03.17.
 */
public class UserJdbcDaoTest extends AbstractJdbcDaoTest<User, Long> {

    public UserJdbcDaoTest() {
        dao = JdbcDaoFactory.getInstance().getUserDao();

    }

    @Override
    protected Long getId(User obj) {
        return obj.getUserId();
    }

    @Override
    protected void setId(User obj, Long id) {
        obj.setUserId(id);
    }

    @Override
    protected User getTestObj() {
        return EntityCreator.createUser();
    }

    @Override
    protected void updateObj(User obj) {
        obj.setFirstName("dfddddddfdfdf");
    }


}
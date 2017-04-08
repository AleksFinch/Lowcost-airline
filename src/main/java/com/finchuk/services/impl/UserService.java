package com.finchuk.services.impl;

import com.finchuk.dao.UserDao;
import com.finchuk.dao.factory.JdbcDaoFactory;
import com.finchuk.dao.jdbc.transaction.Transaction;
import com.finchuk.entities.User;
import com.finchuk.services.AbstractEntityService;
import com.finchuk.services.factory.ServiceFactory;

/**
 * Created by olexandr on 29.03.17.
 */
public class UserService extends AbstractEntityService<User, Long> {
    public UserService() {
        dao = JdbcDaoFactory.getInstance().getUserDao();
    }


    @Override
    protected void setId(User obj, Long id) {
        obj.setUserId(id);
    }

    public User findByEMail(String email){
        return ((UserDao)dao).findByEMail(email);
    }


}

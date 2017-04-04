package com.finchuk.dao;

import com.finchuk.entities.Role;
import com.finchuk.entities.User;

/**
 * Created by olexandr on 25.03.17.
 */
public interface UserDao extends Dao<User, Long> {

    Role readRole(Long userId);

    Long findRoleId(Role role);

    User findByEMail(String eMail);

}

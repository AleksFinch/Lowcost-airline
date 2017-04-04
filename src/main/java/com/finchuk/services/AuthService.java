package com.finchuk.services;

import com.finchuk.entities.User;


import javax.servlet.http.HttpServletRequest;

/**
 * Created by olexandr on 30.03.17.
 */
public interface AuthService {
    boolean login(HttpServletRequest request, String user, String password);

    void logout(HttpServletRequest request);

    boolean register(User user);


    User checkLogin(String username, String password);
}

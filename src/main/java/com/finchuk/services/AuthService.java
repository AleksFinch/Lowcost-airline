package com.finchuk.services;

import com.finchuk.dto.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by olexandr on 30.03.17.
 */
public interface AuthService {
    void init();
    boolean login(HttpServletRequest request, String user, String password);

    void logout(HttpServletRequest request);

    boolean register(User user);


    User checkLogin(String username, String password);
}

package com.finchuk.services.impl;

import com.finchuk.dao.jdbc.transaction.Transaction;
import com.finchuk.entities.Role;
import com.finchuk.entities.User;
import com.finchuk.services.AuthService;
import com.finchuk.util.PasswordEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

/**
 * Created by olexandr on 30.03.17.
 */
public class AuthServiceImpl implements AuthService {
    private static final Logger LOGGER = LogManager.getLogger(AuthServiceImpl.class);

    private static AuthServiceImpl authService = new AuthServiceImpl();

    private static UserService userService = UserService.getInstance();

    private AuthServiceImpl() {

    }

    @Override
    public boolean login(HttpServletRequest request, String user, String password) {
        try {
            request.login(user, password);
            return true;
        } catch (ServletException e) {
            return false;
        }
    }

    @Override
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
        try {
            request.logout();
        } catch (ServletException e) {
            LOGGER.error("Error occurred when user tried to logout", e);
        }
    }

    @Override
    public boolean register(User user) {
        String encodedPass = PasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        user.setRole(Role.USER);
        boolean[] result = new boolean[1];
        Transaction.doTransaction(() -> {
            User exist = userService.find(user.getUserId());
            if (exist == null) {
                userService.add(user);
                result[0] = true;
            } else {
                result[0] = false;
            }
        }, Connection.TRANSACTION_SERIALIZABLE);
        return result[0];
    }

    @Override
    public User checkLogin(String username, String password) {
        User user = userService.findByEMail(username);
        if(user==null)
            return null;
        String encoded = PasswordEncoder.encode(password);
        if(!encoded.equals(encoded)){
            return null;
        }
        return user;
    }

    public static AuthServiceImpl getInstance() {
        return authService;
    }
}

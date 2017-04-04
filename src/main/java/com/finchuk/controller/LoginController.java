package com.finchuk.controller;

import com.finchuk.services.AuthService;
import com.finchuk.services.impl.AuthServiceImpl;

/**
 * Created by root on 04.04.17.
 */
public class LoginController extends Controller {
    private AuthService authService = AuthServiceImpl.getInstance();


    @Override
    public void post(RequestService reqService) {
        String username = reqService.getString("username");
        String password = reqService.getString("password");
        //TODO: validate
        String destination = reqService.getRequest().getHeader("Referer");

        if (authService.login(reqService.getRequest(), username, password)) {
            if(destination.contains("login")) {
                reqService.redirect("/");
            } else {
                reqService.redirect(destination);
            }
        } else {
            reqService.redirect("/login.html?failed=true");
        }
    }
}

package com.finchuk.controller;

import com.finchuk.services.AuthService;
import com.finchuk.services.factory.ServiceFactory;
import com.finchuk.util.Validator;

/**
 * Created by root on 04.04.17.
 */
public class LoginController extends Controller {
    private AuthService authService = ServiceFactory.getAuthService();

    @Override
    public void get(RequestService reqService) {
        reqService.setAtributesFromFlash("username", "error", "login_error");
    }

    @Override
    public void post(RequestService reqService) {
        String result = Validator.validateUserLogin(reqService);
        if(!result.isEmpty()){
            reqService.redirect("/login.html");

            reqService.putFlashParameter("error", result);
            return;
        }
        String username = reqService.getString("username");
        String password = reqService.getString("password");

        String destination = reqService.getRequest().getHeader("Referer");

        if (authService.login(reqService.getRequest(), username, password)) {
            if(destination.contains("login")) {
                reqService.redirect("/");
            } else {
                reqService.redirect(destination);
            }
        } else {
            reqService.redirect("/login.html");
            reqService.putFlashParameter("username", username);
            reqService.putFlashParameter("login_error", true);
        }
    }
}

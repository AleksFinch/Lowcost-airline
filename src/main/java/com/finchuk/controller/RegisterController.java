package com.finchuk.controller;

import com.finchuk.entities.User;
import com.finchuk.services.AuthService;
import com.finchuk.services.impl.AuthServiceImpl;

/**
 * Created by root on 05.04.17.
 */
public class RegisterController extends Controller {
    AuthService service = AuthServiceImpl.getInstance();
    @Override
    public void post(RequestService reqService) {
        String firstname = reqService.getString("firstname");
        String lastname = reqService.getString("lastname");
        String eMail = reqService.getString("e_mail");
        String password = reqService.getString("password");

        User user = new User();
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.seteMail(eMail);
        user.setPassword(password);

        if(service.register(user)){
            reqService.redirect("/login.html");
        }else {
            reqService.redirect("/register?exist=true");
        }
    }
}

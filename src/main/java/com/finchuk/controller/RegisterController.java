package com.finchuk.controller;

import com.finchuk.entities.User;
import com.finchuk.services.AuthService;
import com.finchuk.services.factory.ServiceFactory;
import com.finchuk.util.Validator;

/**
 * Created by root on 05.04.17.
 */
public class RegisterController extends Controller {
    AuthService service = ServiceFactory.getAuthService();

    @Override
    public void get(RequestService reqService) {
        reqService.setAtributesFromFlash("firstname", "lastname", "email", "error");
    }
    @Override
    public void post(RequestService reqService) {
        String result = Validator.validateUserRegister(reqService);
        if(!result.isEmpty()){
            reqService.redirect("/register");
            reqService.putFlashParameter("error", result);
            return;
        }
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
            reqService.redirect("/register");
            reqService.putFlashParameter("firstname", firstname);
            reqService.putFlashParameter("lastname", lastname);
            reqService.putFlashParameter("email", eMail);
            reqService.putFlashParameter("error", "signup.error.email.used");
        }
    }
}

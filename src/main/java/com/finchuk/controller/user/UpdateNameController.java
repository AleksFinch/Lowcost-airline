package com.finchuk.controller.user;

import com.finchuk.controller.Controller;
import com.finchuk.controller.RequestService;
import com.finchuk.dto.User;
import com.finchuk.services.factory.ServiceFactory;
import com.finchuk.services.impl.UserService;
import com.finchuk.util.Validator;

/**
 * Created by root on 13.04.17.
 */
public class UpdateNameController extends Controller {
    UserService service = ServiceFactory.getUserService();

    @Override
    public void get(RequestService reqService) {
        reqService.setAtributesFromFlash("error");
        User user = reqService.getCurrentUser().orElse(new User());

        reqService.setPageAttribute("current_user", user);
    }

    @Override
    public void post(RequestService reqService) {

        String firstname = reqService.getString("firstname");
        String lastname = reqService.getString("lastname");
        String result = Validator.validateUserUpdate(reqService);
        if (!result.isEmpty()) {
            reqService.redirect("/user/edit_pers_info");
            reqService.putFlashParameter("error", result);
            return;
        }
        User user = reqService.getCurrentUser().get();
        if (reqService.getCurrentUser().isPresent()) {
            user.setFirstName(firstname);
            user.setLastName(lastname);
            service.update(user);
            reqService.redirect("/user/personal_cabinet.html");
        }
    }

}

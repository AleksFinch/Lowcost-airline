package com.finchuk.controller.user;

import com.finchuk.controller.Controller;
import com.finchuk.controller.RequestService;
import com.finchuk.dto.User;

/**
 * Created by root on 06.04.17.
 */
public class PersonalCabinetController extends Controller {
    @Override
    public void get(RequestService reqService) {
        User user = reqService.getCurrentUser().orElse(new User());

        reqService.setPageAttribute("current_user", user);}
}

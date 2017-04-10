package com.finchuk.util;

import com.finchuk.controller.RequestService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 09.04.17.
 */
public class Validator {


    public static String validateUserLogin(RequestService reqService) {
        String username = reqService.getString("username");
        String password = reqService.getString("password");

        String result = validateEMail(username);
        if (!result.isEmpty()) {
            return result;
        }
        result = validatePassword(password);
        if (!result.isEmpty()) {
            return result;
        }

        return "";
    }

    public static String validateUserRegister(RequestService reqService) {
        String firstname = reqService.getString("firstname");
        String lastname = reqService.getString("lastname");
        String eMail = reqService.getString("e_mail");
        String password = reqService.getString("password");

        String result = validateName(firstname, "firstname");
        if (!result.isEmpty()) {
            return result;
        }
        result = validateName(lastname, "lastname");
        if (!result.isEmpty()) {
            return result;
        }
        result = validateEMail(eMail);
        if (!result.isEmpty()) {
            return result;
        }
        result = validatePassword(password);
        if (!result.isEmpty()) {
            return result;
        }

        return "";
    }

    public static Long tryParseLong(String value) {
        if (value==null||value.isEmpty()) {
            return null;
        }
        try {
            Long id = Long.valueOf(value);
            return id;
        } catch (NumberFormatException e) {
            return null;
        }

    }

    private static String validateName(String value, String name) {
        if (value == null || value.isEmpty()) {
            return "invalid."+name + ".empty";
        }
        Pattern pattern = Pattern.compile("^[\\wа-яА-ЯіІїЇ@#$%^&+= ]{1,64}");
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            return "invalid." + name;
        }
        return "";
    }

    private static String validateEMail(String value) {
        if (value == null || value.isEmpty()) {
            return "invalid.email.empty";
        }
        Pattern pattern = Pattern.compile("^[\\w]+(\\.[\\w]+)?@[\\w]+(\\.[\\w]+)?$");
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            return "invalid.email";
        }
        return "";
    }

    private static String validatePassword(String value) {
        if (value == null || value.isEmpty()) {
            return "invalid.password.empty";
        }
        Pattern pattern = Pattern.compile("^[а-яА-Я\\wіІїЇ@#$%^&+=]{6,64}$$");
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            return "invalid.password";
        }
        return "";
    }
}

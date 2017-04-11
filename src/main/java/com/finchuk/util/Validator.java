package com.finchuk.util;

import com.finchuk.controller.RequestService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 09.04.17.
 */
public class Validator {

    public static String validateFlight(RequestService reqService) {
        String plane = reqService.getString("plane");
        String airportFrom = reqService.getString("s_airport_from");
        String airportTo = reqService.getString("s_airport_to");
        String startPrice = reqService.getString("start_price");
        String startPriceForBusiness = reqService.getString("start_price_business");

        String result = validateName(plane, "plane", 32);
        if (!result.isEmpty()) {
            return result;
        }
        result = validateName(airportFrom, "airport", 20);
        if (!result.isEmpty()) {
            return result;
        }
        result = validateName(airportTo, "airport", 20);
        if (!result.isEmpty()) {
            return result;
        }
        result = validateDecimal(startPrice);
        if (!result.isEmpty()) {
            return result;
        }
        result = validateDecimal(startPriceForBusiness);
        if (!result.isEmpty()) {
            return result;
        }

        return "";
    }


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

        String result = validateName(firstname, "firstname", 64);
        if (!result.isEmpty()) {
            return result;
        }
        result = validateName(lastname, "lastname", 64);
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


    private static String validateName(String value, String name, int maxSize) {
        if (value == null || value.isEmpty()) {
            return "invalid."+name + ".empty";
        }
        Pattern pattern = Pattern.compile("^[\\wа-яА-ЯіІїЇ@#$%^&+\\-= ]{1," + maxSize + "}$");
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            return "invalid." + name;
        }
        return "";
    }

    private static String validateDecimal(String value) {
        if (value == null || value.isEmpty()) {
            return "invalid.price.empty";
        }
        Pattern pattern = Pattern.compile("^\\d{1,7}(\\.\\d{1,2})?$");
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            return "invalid.price";
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
            return "invalid.imgpath.empty";
        }
        Pattern pattern = Pattern.compile("^[а-яА-Я\\wіІїЇ@#$%^&+=]{6,64}$");
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            return "invalid.imgpath";
        }
        return "";
    }

    private static String validateImgPath(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        Pattern pattern = Pattern.compile("^(.{1,500}\\.(jpe?g|png))$");
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            return "invalid.imgpath";
        }
        return "";
    }

    public static String validateAirport(RequestService reqService) {
        String airportId = reqService.getString("airport_id");
        String country = reqService.getString("country");
        String town = reqService.getString("town");
        String result = validateName(airportId, "airport", 20);
        if (!result.isEmpty()) {
            return result;
        }
        result = validateName(country, "country", 64);
        if (!result.isEmpty()) {
            return result;
        }
        result = validateName(town, "town", 64);
        if (!result.isEmpty()) {
            return result;
        }
        return "";
    }

    public static String validateAirline(RequestService reqService) {
        String company = reqService.getString("company");
        String imgPath = reqService.getString("img_path");
        String result = validateName(company, "company", 64);
        if (!result.isEmpty()) {
            return result;
        }
        result = validateImgPath(imgPath);
        if (!result.isEmpty()) {
            return result;
        }
        return "";

    }
}

package com.finchuk.controller;

import javax.servlet.jsp.jstl.core.Config;
import java.util.HashMap;
import java.util.Map;

public class LocaleController extends Controller {
    private static final Map<String, String> languages = new HashMap<>();

    static {
        languages.put("ua", "uk_UA");
        languages.put("en", "en_EN");
    }

    private static final String FORM_LANG = "lang";

    private static final String SESSION_LOCALE = "locale";

    @Override
    public void post(RequestService reqService) {
        String lang = reqService.getString(FORM_LANG);

        if (lang != null && !lang.isEmpty()) {
            reqService.getRequest().getSession().setAttribute(FORM_LANG, lang);
        } else {
            lang = "en";
        }

        String langString = languages.getOrDefault(lang,"en_EN");

        Config.set(reqService.getRequest().getSession(), Config.FMT_LOCALE,
                langString);
        reqService.getRequest().getSession().setAttribute(SESSION_LOCALE, langString);
    }
}

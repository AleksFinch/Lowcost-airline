package com.finchuk.controller;

import javax.servlet.jsp.jstl.core.Config;

public class LocaleController extends Controller {
    private static final String FORM_LANG = "lang";

    private static final String SESSION_LOCALE = "locale";

    @Override
    public void post(RequestService reqService) {
        String lang = reqService.getString(FORM_LANG);

        if(lang != null && !lang.isEmpty()) {
            reqService.getRequest().getSession().setAttribute(FORM_LANG, lang);
        } else {
            lang = (String) reqService.getRequest().getSession().getAttribute(FORM_LANG);
            if(lang == null) {
                lang = "en";
            }
        }

        String langString = lang + "_" + lang.toUpperCase();

        Config.set(reqService.getRequest().getSession(), Config.FMT_LOCALE,
                langString);
        reqService.getRequest().getSession().setAttribute(SESSION_LOCALE, langString);
    }
}

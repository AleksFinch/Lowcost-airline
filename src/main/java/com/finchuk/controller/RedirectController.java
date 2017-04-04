package com.finchuk.controller;

/**
 * Created by root on 04.04.17.
 */
public class RedirectController extends Controller {
    private final String path;

    public RedirectController(String path) {
        this.path = path;
    }

    @Override
    public void execute(RequestService reqService) {
        reqService.render(path);
    }
}

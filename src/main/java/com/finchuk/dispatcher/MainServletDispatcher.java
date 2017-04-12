package com.finchuk.dispatcher;

import com.finchuk.controller.Controller;
import com.finchuk.controller.RequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by olexandr on 31.03.17.
 */
public class MainServletDispatcher extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(MainServletDispatcher.class);

    public static final String FLASH_SESSION_KEY = "__flash";
    public static final String PAGE_SUFFIX = ".html";
    public static final String REDIRECT_KEY = "__redirect";
    public static final String HEADER_REFERRER = "Referer";

    private HashMap<String, Controller> pathMap = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp);
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String pathInfo = req.getPathInfo();


            if (pathInfo == null) {
                pathInfo = "/";
            }
            Controller controller = getController(pathInfo);
            RequestService requestService = new RequestService(req, resp);
            Optional.ofNullable(controller).ifPresent(e -> e.execute(requestService));
            if (!tryRedirect(requestService)) {
                tryRender(req, resp, requestService);
            }
            if (requestService.isRedirect()) {
                requestService.clearRedirectFlag();
            } else {

                requestService.clearFlash();

            }

            //TODO: release resources

        } catch (Exception e) {
            LOGGER.error("something wrong", e);
            throw e;
        }
    }

    public void addMapping(String url, Controller controller) {
        pathMap.put(url.toLowerCase(), controller);
    }

    private Controller getController(String pathInfo) {
        pathInfo = pathInfo.toLowerCase();
        int index = pathInfo.lastIndexOf(PAGE_SUFFIX);
        if (index != -1 && index + PAGE_SUFFIX.length() == pathInfo.length()) {
            pathInfo = pathInfo.substring(0, index);
        }
        return pathMap.get(pathInfo);

    }

    private boolean tryRedirect(RequestService requestService) {
        if (requestService.getRedirectPath() != null) {
            try {
                requestService.getRequest().getSession().setAttribute(REDIRECT_KEY, true);
                requestService.getResponse().sendRedirect(requestService.getRedirectPath());
                return true;
            } catch (IOException e) {
                LOGGER.warn("An exception happened at redirecting");
            }
        }
        return false;
    }

    private void tryRender(HttpServletRequest req, HttpServletResponse resp, RequestService requestService) {
        if (requestService.getRenderPage() != null) {
            try {
                req.getRequestDispatcher(requestService.getRenderPage()).forward(req, resp);
            } catch (ServletException | IOException e) {
                LOGGER.warn("An exception happened at page rendering phase");
            }
        } else {
            try {
                if (req.getMethod().equals("GET")) {
                    if (resp.getStatus() >= 200 && resp.getStatus() < 300) {
                        req.getRequestDispatcher("/pages/" + appendSuffix(req.getPathInfo()))
                                .forward(req, resp);
                    }
                } else if (req.getHeader(HEADER_REFERRER) != null) {
                    if (resp.getStatus() >= 200 && resp.getStatus() < 300) {
                        resp.sendRedirect(req.getHeader(HEADER_REFERRER));
                    }
                }
            } catch (ServletException | IOException e) {
                LOGGER.warn("An exception happened at page rendering phase");
            }
        }
    }

    private boolean hasPageSuffix(String pathInfo) {
        int index = pathInfo.lastIndexOf(PAGE_SUFFIX);
        return index + PAGE_SUFFIX.length() == pathInfo.length();
    }

    private String appendSuffix(String pathInfo) {
        if (!hasPageSuffix(pathInfo))
            return pathInfo + PAGE_SUFFIX;
        return pathInfo;
    }


}

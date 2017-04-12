package com.finchuk.security;

import com.finchuk.dto.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A singleton object for encapsulating user authorization and authentication
 * Allows adding security constrains to the web application
 */
public class SecurityContainer {

    private static SecurityContainer container = new SecurityContainer();

    private List<RoleConstraint> constraints = new ArrayList<>();

    public static final String LOGIN_PAGE = "/login";

    private SecurityContainer() {
    }

    /**
     * Allows access to URLs denoted by the s regular expression
     * for the given roles and denies for other users
     *
     * @param urlPattern regular expression which tested against the URL
     * @param roles      roles that allowed to access the given set of pages;
     *                   if null - only authenticated user can access the pages
     * @return the same SecurityContext
     */
    public void addConstraint(String urlPattern,List<HTTPMethod> methods, Role... roles) {
        constraints.add(new RoleConstraint(urlPattern, methods, roles));
    }
    public void addConstraint(String urlPattern, Role... roles) {
        addConstraint(urlPattern, Arrays.asList(HTTPMethod.values()),roles);
    }

    public void clear() {
        constraints.clear();
    }

    /**
     * Test if a user with the given roles and request method can access the page
     * @param urlPath  URI of the resource starting with /
     * @param role role of the user
     * @param httpMethod - request method
     * @return true if user allowed to access the resource, false - otherwise
     */
    public boolean isAllowed(String urlPath, HTTPMethod httpMethod, Role role) {
        for (RoleConstraint constraint :
                constraints) {
            if (constraint.matches(urlPath)) {
                if (constraint.isAllowedForRole(role)&&constraint.isAllowedForMethod(httpMethod)) {
                    return true;
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    public static SecurityContainer getInstance() {
        return container;
    }

    private static class RoleConstraint {
        Matcher patternMatcher;
        List<Role> allowedRoles = new ArrayList<>();
        List<HTTPMethod> allowedMethods = new ArrayList<>();

        RoleConstraint(String pathPattern, List<HTTPMethod> methods,Role... roles) {
            this.patternMatcher = Pattern.compile("^" + pathPattern + "$").matcher("/");
            this.allowedMethods.addAll(methods);
            for (Role r :
                    roles) {
                allowedRoles.add(r);
            }
        }

        boolean isAllowedForRole(Role role) {
            if (allowedRoles.isEmpty()) {
                return true;
            }
            return allowedRoles.contains(role);
        }

        boolean isAllowedForMethod(HTTPMethod method) {
            if (allowedMethods.isEmpty()) {
                return true;
            }
            return allowedMethods.contains(method);
        }

        boolean matches(String path) {
            patternMatcher.reset(path);
            return patternMatcher.matches();
        }


    }
}

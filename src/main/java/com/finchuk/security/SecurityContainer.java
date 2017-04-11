package com.finchuk.security;

import com.finchuk.entities.Role;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by olexandr on 30.03.17.
 */
public class SecurityContainer {

    private static SecurityContainer container = new SecurityContainer();

    private List<RoleConstraint> constraints = new ArrayList<>();

    public static final String LOGIN_PAGE = "/login";

    private SecurityContainer() {
    }

    public void addConstraint(String urlPattern,List<HTTPMethod> methods, Role... roles) {
        constraints.add(new RoleConstraint(urlPattern, methods, roles));
    }
    public void addConstraint(String urlPattern, Role... roles) {
        addConstraint(urlPattern, Arrays.asList(HTTPMethod.values()),roles);
    }

    public void clear() {
        constraints.clear();
    }

    public boolean isAllowed(String urlPath,HTTPMethod httpMethod, Role role) {
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

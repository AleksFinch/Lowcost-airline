package com.finchuk.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Custom JSP tag for checking user roles directly in the
 * JSP page. If no `role` attribute is supplied that it will
 * test if the user authentication to the web application.
 * If `role` attribute equals to 'none' that it will
 * test if the user is not authenticated to the web application
 */
public class IfUserInRoleTag extends TagSupport {
    private String role;

    @Override
    public int doStartTag() throws JspException {

        if (role == null) {
            if (getRequest().getUserPrincipal() != null) {
                return EVAL_BODY_INCLUDE;
            }
        } else {
            if (role.equals("none")) {
                if (getRequest().getUserPrincipal() == null) {
                    return EVAL_BODY_INCLUDE;
                }
            } else {
                if (getRequest().isUserInRole(role.toLowerCase())) {
                    return EVAL_BODY_INCLUDE;
                }
            }
        }

        return SKIP_BODY;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) pageContext.getRequest();
    }
}


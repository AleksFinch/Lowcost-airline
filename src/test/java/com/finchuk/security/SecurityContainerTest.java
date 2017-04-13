package com.finchuk.security;

import com.finchuk.dto.Role;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by root on 12.04.17.
 */
public class SecurityContainerTest {
    private SecurityContainer securityContainer;

    @Before
    public void setUp() throws Exception {
        SecurityContainer.getInstance().clear();
        securityContainer = SecurityContainer.getInstance();
    }

    @Test
    public void testAddedConstraintCheckExact() {
        securityContainer.addConstraint("/user/.*", Role.USER);
        assertTrue(securityContainer
                .isAllowed("/user/index.html", HTTPMethod.GET, Role.USER));
    }

    @Test
    public void testAddedConstraintCheckNull() {
        securityContainer.addConstraint("/user/.*", Role.USER);
        assertFalse(securityContainer.isAllowed("/user/index.html", HTTPMethod.GET,
                null));
    }


    @Test
    public void testAddedConstraintCheckNoPermission() throws Exception {
        securityContainer.addConstraint("/user/.*", Role.USER);
        assertFalse(securityContainer.isAllowed("/user/index.html", HTTPMethod.GET,
                Role.ADMIN));
    }


    @Test
    public void testAddedConstraintMissGivenRole() {
        securityContainer.addConstraint("/user/.*", Role.USER);
        assertTrue(securityContainer.isAllowed("/hello.html", HTTPMethod.GET,
                Role.USER));
    }

    @Test
    public void testAddedConstraintMissGivenNoRole() {
        securityContainer.addConstraint("/user/.*", Role.USER);
        assertTrue(securityContainer.isAllowed("/hello.html", HTTPMethod.GET,
                null));
    }

    @Test
    public void testAuthConstraintGivenNoRole() {
        securityContainer.addConstraint("/user/.*");
        assertTrue(securityContainer.isAllowed("/user/index.html", HTTPMethod.GET,
                null));
    }

    @Test
    public void testAuthConstraintGivenRoles() {
        securityContainer.addConstraint("/user/.*");
        assertTrue(securityContainer.isAllowed("/user/index.html", HTTPMethod.GET,
                Role.USER));
        assertTrue(securityContainer.isAllowed("/user/index.html", HTTPMethod.GET,
                Role.ADMIN));
    }
}
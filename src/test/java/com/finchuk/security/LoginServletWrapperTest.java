package com.finchuk.security;

import com.finchuk.dto.Role;
import com.finchuk.dto.User;
import com.finchuk.security.mock.MockHttpSession;
import com.finchuk.services.AuthService;
import com.finchuk.util.PasswordEncoder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.anyBoolean;
import static org.mockito.BDDMockito.given;

/**
 * Created by root on 12.04.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginServletWrapperTest {
    @Mock
    private AuthService authService;

    @Mock
    private HttpServletRequest mockRequest;

    private User user;

    private User admin;

    private LoginServletWrapper request;

    @Before
    public void setUp() throws Exception {

        user = getUser();
        admin = getAdmin();

        given(authService.checkLogin(user.geteMail(), "password")).willReturn(user);
        given(authService.checkLogin(admin.geteMail(), "password")).willReturn(admin);

        HttpSession session = new MockHttpSession();
        given(mockRequest.getSession()).willReturn(session);
        given(mockRequest.getSession(anyBoolean())).willReturn(session);
        request = new LoginServletWrapper(mockRequest);
        request.setService(authService);
    }

    @Test
    public void testNoCurrentUser() {
        assertNull(request.getCurrentUser());
    }

    @Test
    public void testLogin() throws ServletException {
        request.login("fin@gmail.com", "password");
        assertNotNull(request.getCurrentUser());
    }

    @Test(expected = ServletException.class)
    public void testLoginBadCredentials() throws ServletException {
        request.login("fin@gmail.com", "invalid");
    }

    @Test
    public void testLogoutAfterLogin() throws ServletException {
        request.login("fin@gmail.com", "password");
        request.logout();
        assertNull(request.getCurrentUser());
    }

    @Test
    public void testLogoutWithoutLogin() throws ServletException {
        request.logout();
        assertNull(request.getCurrentUser());
    }

    @Test
    public void testGetUserPrincipalAfterLogin() throws ServletException {
        request.login("fin@gmail.com", "password");
        assertNotNull(request.getUserPrincipal());
        assertEquals("1", request.getUserPrincipal().getName());
    }

    @Test
    public void testGetUserPrincipalWithoutLogin() throws ServletException {
        assertNull(request.getUserPrincipal());
    }

    @Test
    public void testIsUserInRoleHit() throws ServletException {
        request.login("adm@gmail.com", "password");
        assertTrue(request.isUserInRole(Role.ADMIN.name().toLowerCase()));
    }

    @Test
    public void testIsUserInRoleMiss() throws ServletException {
        request.login("fin@gmail.com", "password");
        assertFalse(request.isUserInRole(Role.ADMIN.name().toLowerCase()));
    }


    private static User getUser() {
        User user = new User();
        user.setUserId(1L);
        user.seteMail("fin@gmail.com");
        user.setRole(Role.USER);
        user.setFirstName("user");
        user.setLastName("puser");
        user.setPassword(PasswordEncoder.encode("password"));
        return user;
    }

    private static User getAdmin() {
        User user = new User();
        user.setUserId(2L);
        user.seteMail("adm@gmail.com");
        user.setRole(Role.ADMIN);
        user.setFirstName("admin");
        user.setLastName("kamin");
        user.setPassword(PasswordEncoder.encode("password"));
        return user;
    }


}
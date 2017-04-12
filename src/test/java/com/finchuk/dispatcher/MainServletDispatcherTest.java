package com.finchuk.dispatcher;

import com.finchuk.controller.Controller;
import com.finchuk.controller.RequestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by root on 12.04.17.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainServletDispatcherTest {
    @Spy
    private Controller controller;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private MainServletDispatcher dispatcher;


    @Before
    public void setUp() throws Exception {
        dispatcher = new MainServletDispatcher();
        given(request.getSession()).willReturn(session);
        given(request.getRequestDispatcher(any())).willReturn(mock(RequestDispatcher.class));
    }

    @Test
    public void testSingleMethodMappingHit() throws ServletException, IOException {
        given(request.getPathInfo()).willReturn("/path1/path2");
        given(request.getMethod()).willReturn("GET");

        dispatcher.addMapping("/path1/path2", controller);
        dispatcher.service(request, response);

        verify(controller, times(1)).get(any());
        verify(controller, never()).post(any());
    }

    @Test
    public void testMultipleControllerMappedToSamePath() throws ServletException, IOException {
        given(request.getPathInfo()).willReturn("/path1/path2");
        given(request.getMethod()).willReturn("GET");

        Controller serviceController = spy(Controller.class);

        dispatcher.addMapping("/path1/path2", controller);
        dispatcher.addMapping("/path1/path2", serviceController);

        dispatcher.service(request, response);

        verify(controller, never()).get(any());
        verify(serviceController, times(1)).get(any());
    }


    @Test
    public void testSingleMethodMappingMiss() throws ServletException, IOException {
        given(request.getPathInfo()).willReturn("/path1/path");
        given(request.getMethod()).willReturn("GET");

        dispatcher.addMapping("/path1/path2", controller);
        dispatcher.service(request, response);

        verify(controller, never()).get(any());
    }

    @Test
    public void testSeveralMethodMappingHit() throws ServletException, IOException {
        given(request.getPathInfo()).willReturn("/path1/path2", "/path1/path2");
        given(request.getMethod()).willReturn("GET");

        dispatcher.addMapping("/path1/path2", controller);

        dispatcher.service(request, response);
        verify(controller, times(1)).get(any());
        verify(controller, never()).post(any());

        given(request.getMethod()).willReturn("POST");

        dispatcher.service(request, response);
        verify(controller, times(1)).get(any());
        verify(controller, times(1)).post(any());
    }


    @Test
    public void testRenderPage() throws ServletException, IOException {
        given(request.getPathInfo()).willReturn("/path1/path2", "/path1/path2");
        given(request.getMethod()).willReturn("GET");

        dispatcher.addMapping("/path1/path2", new Controller() {
            @Override
            public void get(RequestService reqService) {
                reqService.render("/index.html");
            }
        });

        dispatcher.service(request, response);

        ArgumentCaptor<String> pathCaptor = ArgumentCaptor.forClass(String.class);
        verify(request, times(1)).getRequestDispatcher(pathCaptor.capture());
        assertEquals("/index.html", pathCaptor.getValue());
    }

    @Test
    public void testRedirect() throws ServletException, IOException {
        given(request.getPathInfo()).willReturn("/path1/path2", "/path1/path2");
        given(request.getMethod()).willReturn("GET");

        dispatcher.addMapping("/path1/path2", new Controller() {
            @Override
            public void get(RequestService reqService) {
                reqService.redirect("/index.html");
            }
        });

        dispatcher.service(request, response);

        ArgumentCaptor<String> pathCaptor = ArgumentCaptor.forClass(String.class);
        verify(response, times(1)).sendRedirect(pathCaptor.capture());
        assertEquals("/index.html", pathCaptor.getValue());
    }


}
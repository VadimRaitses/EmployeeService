package com.employeeservice.auth;

import com.employeeservice.models.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static com.employeeservice.auth.SecurityConstants.HEADER_STRING;
import static org.mockito.Mockito.*;

public class JWTAuthorizationFilterTest {


    private JWTAuthorizationFilterMock classUnderTest;
    private AuthenticationManager authManager = mock(AuthenticationManager.class);

    private HttpServletRequest req = mock(HttpServletRequest.class);
    private HttpServletResponse res = mock(HttpServletResponse.class);
    private FilterChain chain = mock(FilterChain.class);
    private Account acc;


    @Before
    public void setup() {
        acc = new Account("vadik", "va", "abc.com");
        classUnderTest = new JWTAuthorizationFilterMock(authManager);
    }


    @Test
    public void authorizeSuccess() {
        when(req.getHeader(HEADER_STRING)).thenReturn("token HEADER");
        boolean actual = classUnderTest.authorize(req, res, chain);
        Assert.assertTrue(actual);
    }


    @Test
    public void authenticatedSuccess() {
        when(req.getHeader(HEADER_STRING)).thenReturn("Bearer HEADER");
        boolean actual = classUnderTest.authorize(req, res, chain);
        Assert.assertTrue(actual);
    }


    @Test
    public void authorizeNotSucceded() throws Exception{
        when(req.getHeader(HEADER_STRING)).thenReturn("not token");
        doThrow(RuntimeException.class).when(chain).doFilter(isA(HttpServletRequest.class),isA(HttpServletResponse.class));
        boolean actual = classUnderTest.authorize(req, res, chain);
        Assert.assertFalse(actual);
    }




    class JWTAuthorizationFilterMock extends JWTAuthorizationFilter {

        JWTAuthorizationFilterMock(AuthenticationManager authManager) {
            super(authManager);
        }


        @Override
        UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
            return new UsernamePasswordAuthenticationToken(acc, null, new ArrayList<>());
        }
    }
}
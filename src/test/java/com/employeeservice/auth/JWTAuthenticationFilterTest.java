package com.employeeservice.auth;

import com.employeeservice.models.Account;
import com.employeeservice.services.AccountService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JWTAuthenticationFilterTest {


    private JWTAuthenticationFilter classUnderTest;
    private final AuthenticationManager authManager = mock(AuthenticationManager.class);
    private final AccountService userDetailsService = mock(AccountService.class);
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse res = mock(HttpServletResponse.class);
    private final FilterChain chain = mock(FilterChain.class);
    private final String url = "/token/";
    private Account acc;
    private ServletInputStream servletInputStream;
    private ByteArrayInputStream byteArrayInputStream;
    private UsernamePasswordAuthenticationToken uToken;
    private final AutType expectedType = new AutType();

    @Before
    public void setup() {


        acc = new Account("va", "va");
        classUnderTest = new JWTAuthenticationFilter(url, authManager, userDetailsService);

        String myString = "{\"email\":\"va\",\"password\": \"va\"}";
        byteArrayInputStream = new ByteArrayInputStream(myString.getBytes(StandardCharsets.UTF_8));
        servletInputStream = new ServletInputStream() {
            @Override
            public int read() {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        };

        uToken = new UsernamePasswordAuthenticationToken(
                "va",
                "ac",
                new ArrayList<>());

    }

    @Test
    public void attemptAuthentication() throws Exception {

        when(req.getInputStream()).thenReturn(servletInputStream);
        when(authManager.authenticate(isA(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(expectedType);
        Authentication actual = classUnderTest.attemptAuthentication(req, res);
        Assert.assertEquals(expectedType, actual);
    }

    @Test
    public void successfulAuthentication() {
    }

    class AutType implements Authentication {

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }

        @Override
        public boolean isAuthenticated() {
            return false;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return null;
        }
    }

}
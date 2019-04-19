package com.employeeservice.auth;

import com.employeeservice.exceptions.GlobalExceptionHandler;
import com.employeeservice.models.Account;
import com.employeeservice.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;



/**
 * @author Raitses Vadim
 */

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private AuthenticationManager authenticationManager;
    private AccountService userDetailsService;

    public JWTAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, AccountService userDetailsService) {
        super(defaultFilterProcessesUrl);
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        try {
            Account credentials = new ObjectMapper()
                    .readValue(httpServletRequest.getInputStream(), Account.class);

            String currentPassword = credentials.getPassword();
            if (userDetailsService.getAccount(credentials.getEmail()) == null)
                userDetailsService.addAccount(credentials);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getEmail(),
                            currentPassword,
                            new ArrayList<>())
            );
        } catch (IOException e) {
            LOGGER.error(":attemptAuthentication:error during authorization" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth)  {
        TokenAuthenticationService.addAuthentication(res, auth);
    }
}
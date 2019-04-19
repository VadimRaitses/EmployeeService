package com.employeeservice.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.employeeservice.auth.SecurityConstants.HEADER_STRING;
import static com.employeeservice.auth.SecurityConstants.TOKEN_PREFIX;



/**
 * @author Raitses Vadim
 */

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)  {
        authorize(req, res, chain);
    }

    boolean authorize(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
        try {
            String header = req.getHeader(HEADER_STRING);
            if (header == null || !header.startsWith(TOKEN_PREFIX)) {
                chain.doFilter(req, res);
                return true;
            }
            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
            return true;
        } catch (Exception e) {
            LOGGER.error(":doFilterInternal:error during authorization" + e.getMessage());
            return false;
        }
    }


    UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        return TokenAuthenticationService.getAuthentication(request);

    }
}
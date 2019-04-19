package com.employeeservice.auth;

/**
 * @author Raitses Vadim
 */


class SecurityConstants {

    final static String SECRET = "SecretKeyToGenJWTs";
    final static long EXPIRATION_TIME = 864_000_000; // 10 days
    final static String TOKEN_PREFIX = "Bearer ";
    final static String HEADER_STRING = "Authorization";
    final static String SIGN_UP_URL = "/token/";

}
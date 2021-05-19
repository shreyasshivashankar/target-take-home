package com.myretail.productapi.server;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class TokenValidityFilter implements Filter {

    public static final String HEADER_NAME = "access_token";
    public static final String HEADER_VALUE = "test_token_uid_encrypt";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String header = httpServletRequest.getHeader(HEADER_NAME);
            if (HEADER_VALUE.equals(header)) {
                // If the user is authorizable
                RequestValues.setTokenValidity(true);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // We need to reset the access
            RequestValues.setTokenValidity(false);
        }
    }

}

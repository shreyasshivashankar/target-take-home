package com.myretail.productapi.auth;

import com.myretail.productapi.server.RequestValues;
import com.myretail.productapi.exceptions.AuthorizationException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequiresTokenAspect {
    @Before("@annotation(RequiresToken)")
    void requiresToken() {
        if(!RequestValues.isTokenValid()) {
            throw new AuthorizationException("You are not authorised to perform this request!");
        }
    }
}

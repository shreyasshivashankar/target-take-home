package com.myretail.productapi.server;

/**
 * Simple class with a thread local for storing header auth information from http requests
 *
 * Created by shreyasshivashankar on 14/05/2021.
 */
public class RequestValues {
    /**
     * For each incoming request, we determine whether the caller has the required token.
     */
    private static final ThreadLocal<Boolean> isTokenValidThreadLocal = new ThreadLocal<>();

    static void setTokenValidity(boolean isTokenValid) {
        isTokenValidThreadLocal.set(isTokenValid);
    }

    static public boolean isTokenValid() {
        if (isTokenValidThreadLocal.get() == null) {
            isTokenValidThreadLocal.set(false);
        }
        return isTokenValidThreadLocal.get();
    }
}

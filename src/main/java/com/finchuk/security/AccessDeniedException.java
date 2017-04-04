package com.finchuk.security;

/**
 * Created by olexandr on 30.03.17.
 */
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(Throwable cause) {
        super(cause);
    }

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
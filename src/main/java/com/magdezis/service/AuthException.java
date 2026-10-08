package com.magdezis.service;

public class AuthException extends Exception {

    public AuthException(String mensaje) {
        super(mensaje);
    }

    public AuthException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

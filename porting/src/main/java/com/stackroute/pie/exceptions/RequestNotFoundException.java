package com.stackroute.pie.exceptions;

public class RequestNotFoundException extends Exception{
    private String message;

    public RequestNotFoundException() {
    }

    public RequestNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}

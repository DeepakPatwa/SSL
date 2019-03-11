package com.stackroute.pie.exceptions;

public class PolicyNotFoundException extends Exception{
    private String message;
    public PolicyNotFoundException(String message){
        super(message);
        this.message = message;
    }

    public PolicyNotFoundException() {
    }
}

package com.stackroute.pie.exceptions;

public class PolicyAlreadyExistsException extends Exception{
    private String message;
    public PolicyAlreadyExistsException(String message){
        super(message);
        this.message = message;
    }

    public PolicyAlreadyExistsException() {
    }
}

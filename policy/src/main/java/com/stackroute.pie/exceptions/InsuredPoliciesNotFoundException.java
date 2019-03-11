package com.stackroute.pie.exceptions;

public class InsuredPoliciesNotFoundException extends Exception {
    private String message;
    public InsuredPoliciesNotFoundException(String message){
        super(message);
        this.message = message;
    }

    public InsuredPoliciesNotFoundException() {
    }
}

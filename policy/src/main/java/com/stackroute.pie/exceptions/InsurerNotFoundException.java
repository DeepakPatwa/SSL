package com.stackroute.pie.exceptions;

import com.stackroute.pie.domain.Policy;

import java.util.List;

public class InsurerNotFoundException extends Exception {
    private String message;
    public InsurerNotFoundException(String message){
        super(message);
        this.message = message;
    }

    public InsurerNotFoundException() {
    }
}
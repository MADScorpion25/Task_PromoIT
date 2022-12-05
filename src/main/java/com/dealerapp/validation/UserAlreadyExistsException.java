package com.dealerapp.validation;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String login) {
        super("User with login " + login + " is already exists" );
    }
}

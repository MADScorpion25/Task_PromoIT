package com.dealerapp.validation.exceptions;

public class UserNotFoundException extends EntityNotFoundException{
    public UserNotFoundException(long id){
        super("User with id = " + id + " not found");
    }

    public UserNotFoundException(String name){
        super("User with login = " + name + "not found");
    }
}

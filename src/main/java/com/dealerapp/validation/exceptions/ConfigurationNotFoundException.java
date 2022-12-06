package com.dealerapp.validation.exceptions;

public class ConfigurationNotFoundException extends EntityNotFoundException{
    public ConfigurationNotFoundException(long id){
        super("Configuration with id = " + id + " not found");
    }

    public ConfigurationNotFoundException(String name){
        super("Configuration with name = " + name + "not found");
    }
}

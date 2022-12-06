package com.dealerapp.validation.exceptions;

public class ConfigurationAlreadyExists extends Exception{
    public ConfigurationAlreadyExists(String name) {
        super("Configuration with name " + name + " is already exists" );
    }
}

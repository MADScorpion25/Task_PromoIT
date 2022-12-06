package com.dealerapp.validation.exceptions;

public class CarModelAlreadyExistsException extends Exception{
    public CarModelAlreadyExistsException(String model) {
        super("Car with model " + model + " is already exists" );
    }
}

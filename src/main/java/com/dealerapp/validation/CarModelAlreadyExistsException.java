package com.dealerapp.validation;

public class CarModelAlreadyExistsException extends Exception{
    public CarModelAlreadyExistsException(String model) {
        super("Car with model " + model + " is already exists" );
    }
}

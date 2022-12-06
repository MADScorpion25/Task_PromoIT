package com.dealerapp.validation.exceptions;

public class CarNotFoundException extends EntityNotFoundException{
    public CarNotFoundException(long id){
        super("Car with id = " + id + " not found");
    }
}

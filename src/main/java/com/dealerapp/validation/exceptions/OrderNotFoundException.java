package com.dealerapp.validation.exceptions;

public class OrderNotFoundException extends EntityNotFoundException{
    public OrderNotFoundException(long id){
        super("Order with id = " + id + " not found");
    }
}

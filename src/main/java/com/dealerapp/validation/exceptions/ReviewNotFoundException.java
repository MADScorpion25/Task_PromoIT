package com.dealerapp.validation.exceptions;

public class ReviewNotFoundException extends EntityNotFoundException{
    public ReviewNotFoundException(long id){
        super("Review with id = " + id + " not found");
    }
}

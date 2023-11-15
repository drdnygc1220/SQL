package com.tpe.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {//constructor ile yaptık tek parametreli olanı ectik.
        super(message);
    }
}

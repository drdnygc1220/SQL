package com.tpe.exception;

public class ConflictException extends RuntimeException{
    public ConflictException(String message){
        super(message);//super :runtimeexceptionı parametreli constr cagırmış oluyoruz.
    }
}

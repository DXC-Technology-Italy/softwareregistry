package com.cm.dev.exception;

/**
 * Generic exception on Service objects
 * 
 */

public class ServiceException extends Exception{

    public ServiceException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
    public ServiceException(String errorMessage){
        super("Error on service :" + errorMessage );
    }
    public ServiceException(Throwable err){
        super(err);
    }
}

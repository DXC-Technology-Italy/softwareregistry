package com.cm.dev.exception;

import com.mongodb.MongoException;

/**
 * Used when a method is not implemented yet
 * 
 */
public class NotImplementedException extends MongoException {

    public NotImplementedException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
    public NotImplementedException(){
        super("Not implemented");
    }

}

package com.example.demo.Crosscutting.exception.data;

import com.example.demo.Crosscutting.enumeration.LayerException;
import com.example.demo.Crosscutting.exception.RetoCustomException;

public class DataCustomException extends RetoCustomException{
    
    protected DataCustomException(String userMessage, String technicalMessage, Throwable rootException) {
        super(userMessage, technicalMessage, rootException, LayerException.DATA);
    }
    
    public static final RetoCustomException CreateUserException(final String userMessage) {
        return new DataCustomException(userMessage, userMessage, new Exception());
        
    }
    
    public static final RetoCustomException CreateTechnicalException(final String technicalMessage) {
        return new DataCustomException(null, technicalMessage, new Exception());
        
    }
    public static final RetoCustomException CreateTechnicalException(final String technicalMessage, final Exception rootException ) {
        return new DataCustomException(null, technicalMessage,rootException);
        
    }
    public static final RetoCustomException Create(final String userMessage, final String technicalMessage) {
        return new DataCustomException(userMessage, technicalMessage, new Exception());
        
    }
    public static final RetoCustomException Create(final String userMessage, final String technicalMessage, final Exception rootException ) {
        return new DataCustomException(userMessage, technicalMessage, rootException);
    }
    
}

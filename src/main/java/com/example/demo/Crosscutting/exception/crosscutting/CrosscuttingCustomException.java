package com.example.demo.Crosscutting.exception.crosscutting;

import com.example.demo.Crosscutting.enumeration.LayerException;
import com.example.demo.Crosscutting.exception.RetoCustomException;

public class CrosscuttingCustomException extends RetoCustomException{

    protected CrosscuttingCustomException(String userMessage, String technicalMessage, Throwable rootException) {
        super(userMessage, technicalMessage, rootException, LayerException.CROSSCUTTING);
    }
    
    public static final RetoCustomException CreateUserException(final String userMessage) {
        return new CrosscuttingCustomException(userMessage, userMessage, new Exception());
        
    }
    
    public static final RetoCustomException CreateTechnicalException(final String technicalMessage) {
        return new CrosscuttingCustomException(null, technicalMessage, new Exception());
        
    }
    public static final RetoCustomException CreateTechnicalException(final String technicalMessage, final Exception rootException ) {
        return new CrosscuttingCustomException(null, technicalMessage,rootException);
        
    }
    public static final RetoCustomException Create(final String userMessage, final String technicalMessage) {
        return new CrosscuttingCustomException(userMessage, technicalMessage, new Exception());
        
    }
    public static final RetoCustomException Create(final String userMessage, final String technicalMessage, final Exception rootException ) {
        return new CrosscuttingCustomException(userMessage, technicalMessage, rootException);
    }
    
}

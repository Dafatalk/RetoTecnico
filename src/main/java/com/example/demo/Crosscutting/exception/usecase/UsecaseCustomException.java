package com.example.demo.Crosscutting.exception.usecase;
import static com.example.demo.Crosscutting.helper.StringHelper.EMPTY;
import com.example.demo.Crosscutting.enumeration.LayerException;
import com.example.demo.Crosscutting.exception.RetoCustomException;

public class UsecaseCustomException extends RetoCustomException{

    protected UsecaseCustomException(String userMessage, String technicalMessage, Throwable rootException) {
        super(userMessage, technicalMessage, rootException, LayerException.SERVICE);
    }
    
	public static final RetoCustomException CreateUserException(final String userMessage) {
	    return new UsecaseCustomException(userMessage, userMessage, new Exception());
	    
	}
	            
	public static final RetoCustomException CreateTechnicalException(final String technicalMessage) {
	    return new UsecaseCustomException(EMPTY, technicalMessage, new Exception());
	    
	}
	public static final RetoCustomException CreateTechnicalException(final String technicalMessage, final Exception rootException) {
	    return new UsecaseCustomException(EMPTY, technicalMessage,rootException);
	    
	}
	public static final RetoCustomException CreateBusinessException(final String businessMessage, final Exception rootException) {
	    return new UsecaseCustomException(businessMessage, EMPTY,rootException);
	    
	}
	public static final RetoCustomException Create(final String userMessage, final String technicalMessage) {
	    return new UsecaseCustomException(userMessage, technicalMessage, new Exception());
	    
	}
	public static final RetoCustomException Create(final String userMessage, final String technicalMessage, final Exception rootException ) {
	    return new UsecaseCustomException(userMessage, technicalMessage, rootException);
	}
	public static final RetoCustomException wrapException(final String message, final RetoCustomException exception){
            if(exception.isTechinalException()){
                return UsecaseCustomException.CreateBusinessException(message, exception);
            }
            return exception;
	}

    
}

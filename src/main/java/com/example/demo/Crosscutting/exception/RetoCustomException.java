package com.example.demo.Crosscutting.exception;

import com.example.demo.Crosscutting.enumeration.LayerException;
import com.example.demo.Crosscutting.helper.ObjectHelper;
import com.example.demo.Crosscutting.helper.StringHelper;

public class RetoCustomException extends RuntimeException{
    
	
	private static final long serialVersionUID = -1776237992981511029L;

	private String userMessage;
	private  LayerException layer;
	    
	            
	    protected RetoCustomException(final String userMessage, final String technicalMessage, final Throwable rootException, final LayerException layer) {
	        super(technicalMessage, ObjectHelper.getDefaultIfNull(rootException, new Exception()));
	        setUserMessage(userMessage);
	        setLayer(layer);
	    }


	    public String getUserMessage() {
	        return userMessage;
	    }


	    public void setUserMessage(final String userMessage) {
	        this.userMessage = StringHelper.applyTrim(userMessage);
	    }


	    public LayerException getLayer() {
	        return layer;
	    }


	    public void setLayer(LayerException layer) {
	        this.layer = ObjectHelper.getDefaultIfNull(layer, LayerException.APLICATION);
	    }
	    
	    public final boolean isTechinalException() {
	        return ObjectHelper.isNull(getUserMessage());
	    }

}


package com.example.demo.Service.usecase.usecaseimpl;

import com.example.demo.Crosscutting.exception.RetoCustomException;
import com.example.demo.Crosscutting.exception.usecase.UsecaseCustomException;
import com.example.demo.Data.daofactory.DAOFactory;
import com.example.demo.Service.usecase.CancelEventUsecase;

public class CancelEventUsecaseImpl implements CancelEventUsecase{
    private final DAOFactory factory;

    public CancelEventUsecaseImpl(DAOFactory factory){
        this.factory=factory;

    }


    @Override
    public void execute(String name) {
        try{
            factory.getEventDAO().delete(name);
        }catch(UsecaseCustomException exception) {
            throw exception;
        } catch(RetoCustomException exception) {
            throw UsecaseCustomException.wrapException("OCURRIO UN PROBLEMA ELIMINANDO EL EVENTO", exception);
        } catch(Exception exception) {
            throw UsecaseCustomException.CreateBusinessException("OCURRIO UN PROBLEMA INESPERADO ELIMINANDO EL EVENTO", exception);
        }
        
    }        
    }
    

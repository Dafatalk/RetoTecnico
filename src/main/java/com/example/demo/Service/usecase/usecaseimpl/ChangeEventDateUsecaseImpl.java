package com.example.demo.Service.usecase.usecaseimpl;

import java.sql.Date;

import com.example.demo.Crosscutting.exception.RetoCustomException;
import com.example.demo.Crosscutting.exception.usecase.UsecaseCustomException;
import com.example.demo.Data.daofactory.DAOFactory;
import com.example.demo.Domain.EventOB;
import com.example.demo.Service.usecase.ChangeEventDateUsecase;

public class ChangeEventDateUsecaseImpl implements ChangeEventDateUsecase{
    private final DAOFactory factory;

    public ChangeEventDateUsecaseImpl(DAOFactory factory){
        this.factory=factory;

    }

    @Override
    public void execute(EventOB event, Date fecha) {
        try{
            event.setDate(fecha);
            factory.getEventDAO().update(event);
                                                 
         }catch(UsecaseCustomException exception) {
             throw exception;
         } catch(RetoCustomException exception) {
             throw UsecaseCustomException.wrapException("OCURRIÓ UN PROBLEMA MODIFICANDO LA FECHA", exception);
         } catch(Exception exception) {
             throw UsecaseCustomException.CreateBusinessException("OCURRIÓ UN ERROR MODIFICANDO LA FECHA", exception);
        
            }
        
    }
}

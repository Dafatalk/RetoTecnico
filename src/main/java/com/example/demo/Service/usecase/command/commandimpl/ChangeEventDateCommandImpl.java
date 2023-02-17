package com.example.demo.Service.usecase.command.commandimpl;

import java.sql.Date;

import com.example.demo.Crosscutting.exception.RetoCustomException;
import com.example.demo.Crosscutting.exception.usecase.UsecaseCustomException;
import com.example.demo.Data.daofactory.DAOFactory;
import com.example.demo.Data.enumeration.DAOFactoryType;
import com.example.demo.Domain.EventOB;
import com.example.demo.Service.usecase.ChangeEventDateUsecase;
import com.example.demo.Service.usecase.command.ChangeEventDateCommand;
import com.example.demo.Service.usecase.usecaseimpl.ChangeEventDateUsecaseImpl;

public class ChangeEventDateCommandImpl implements ChangeEventDateCommand{
    private final DAOFactory factory = DAOFactory.getDAOFactory(DAOFactoryType.POSTGRESQL);
    private final ChangeEventDateUsecase casoDeUso = new ChangeEventDateUsecaseImpl(factory);

    @Override
    public void execute(EventOB event, Date date) {
        try {
            factory.initTransaction();
            casoDeUso.execute(event, date);
            factory.confirmTransaction();
        } catch(UsecaseCustomException exception){
            factory.cancelTransaction();
            throw exception;
        } catch(RetoCustomException exception) {
            factory.cancelTransaction(); 
            throw UsecaseCustomException.wrapException("PROBLEMA CAMBIANDO LA FECHA DEL EVENTO", exception);
        } catch(final Exception exception){
            factory.cancelTransaction(); 
            throw UsecaseCustomException.CreateBusinessException("PROBLEMA INESPERADOS CAMBIANDO LA FECHA DEL EVENTO", exception);
        }        
    }
}

package com.example.demo.Service.usecase.command.commandimpl;

import java.util.List;

import com.example.demo.Crosscutting.exception.RetoCustomException;
import com.example.demo.Crosscutting.exception.usecase.UsecaseCustomException;
import com.example.demo.Data.daofactory.DAOFactory;
import com.example.demo.Data.enumeration.DAOFactoryType;
import com.example.demo.Domain.EventOB;
import com.example.demo.Service.usecase.ListMontEventUsecase;
import com.example.demo.Service.usecase.command.ListMontEventCommand;
import com.example.demo.Service.usecase.usecaseimpl.ListMontEventUsecaseImpl;

public class ListMontEventCommandImpl implements ListMontEventCommand{
    private final DAOFactory factory = DAOFactory.getDAOFactory(DAOFactoryType.POSTGRESQL);
    private final ListMontEventUsecase casoDeUso = new ListMontEventUsecaseImpl(factory);

    @Override
    public  List<EventOB> execute(Integer month) {
        try {
            factory.initTransaction();
            List<EventOB> eventos =  casoDeUso.execute(month);
            factory.confirmTransaction();
            return eventos;
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

package com.example.demo.Service.usecase.command.commandimpl;

import com.example.demo.Crosscutting.exception.RetoCustomException;
import com.example.demo.Crosscutting.exception.usecase.UsecaseCustomException;
import com.example.demo.Data.daofactory.DAOFactory;
import com.example.demo.Data.enumeration.DAOFactoryType;
import com.example.demo.Domain.EventOB;
import com.example.demo.Service.usecase.ScheduleEventUsecase;
import com.example.demo.Service.usecase.command.ScheduleEventCommand;
import com.example.demo.Service.usecase.usecaseimpl.ScheduleEventUsecaseImpl;

public class ScheduleEventCommandImpl implements ScheduleEventCommand{
    private final DAOFactory factory = DAOFactory.getDAOFactory(DAOFactoryType.POSTGRESQL);
    private final ScheduleEventUsecase casoDeUso = new ScheduleEventUsecaseImpl(factory);

    @Override
    public void execute(EventOB event) {
        try {
            factory.initTransaction();
            casoDeUso.execute(event);
            factory.confirmTransaction();
        } catch(UsecaseCustomException exception){
            factory.cancelTransaction();
            throw exception;
        } catch(RetoCustomException exception) {
            factory.cancelTransaction(); 
            throw UsecaseCustomException.wrapException("PROBLEMA AGENDANDO EL EVENTO", exception);
        } catch(final Exception exception){
            factory.cancelTransaction(); 
            throw UsecaseCustomException.CreateBusinessException("PROBLEMA INESPERADOS AGENDANDO EL EVENTO", exception);
        }        
    }
}

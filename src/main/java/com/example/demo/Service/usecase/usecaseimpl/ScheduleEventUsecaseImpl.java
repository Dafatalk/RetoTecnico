package com.example.demo.Service.usecase.usecaseimpl;

import java.sql.Date;
import java.util.List;

import com.example.demo.Crosscutting.exception.RetoCustomException;
import com.example.demo.Crosscutting.exception.usecase.UsecaseCustomException;
import com.example.demo.Crosscutting.helper.DateHelper;
import com.example.demo.Crosscutting.helper.StringHelper;
import com.example.demo.Data.daofactory.DAOFactory;
import com.example.demo.Domain.EventOB;
import com.example.demo.Domain.RoomOB;
import com.example.demo.Service.usecase.ScheduleEventUsecase;

public class ScheduleEventUsecaseImpl implements ScheduleEventUsecase{
    private final DAOFactory factory;
    private final SearcRoomUsecaseImpl searchRoom;
    private final ListMontEventUsecaseImpl listMonth;

    public ScheduleEventUsecaseImpl(DAOFactory factory){
        this.factory=factory;
        searchRoom = new SearcRoomUsecaseImpl(factory);
        listMonth = new ListMontEventUsecaseImpl(factory);
    }

    @Override
    public void execute(EventOB event) {
        try {
            final String name = validateName(event.getName()); 
            final Date date = validateDate(event.getDate());
            final RoomOB room = validateDisponibility(event);//TODO
            
            event.setName(name);
            event.setDate(date);
            event.setRoomOB(room);
            
            factory.getEventDAO().create(event);
            
        } catch(RetoCustomException exepcion) {
            throw UsecaseCustomException.wrapException("PROBLEMA PARA AGENDAR EL EVENTO", exepcion);
        } catch(Exception exepcion) {
            throw UsecaseCustomException.CreateBusinessException("PROBLEMA INESPERADO PARA AGENDAR EL EVENTO", exepcion);
        }        
    }   

    private final String validateName(String name){
        if(StringHelper.isDefaultString(name)){
            throw UsecaseCustomException.CreateUserException("EL NOMBRE NO PUEDE IR VACIO");
        }
        
        return name;
    }
    private final Date validateDate(Date date){
        if(DateHelper.isDefaultDate(date)){
            throw UsecaseCustomException.CreateUserException("NO SE PUEDE MANDAR LA FECHA VACIA");
        }
        return date;

    }
    private final RoomOB validateDisponibility(EventOB event){
        final RoomOB salonE = searchRoom.execute(event.getRoomOB().getName());
        final List<EventOB> events = listMonth.execute(event.getDate().toLocalDate().getMonthValue());

        if(!salonE.exist()){
            throw UsecaseCustomException.CreateUserException("ESTE SALON NO EXISTE");
        }
        if(exist(events, event)){
            throw UsecaseCustomException.CreateUserException("ESTE SALON YA ESTÁ OCUPADO PARA ESTE DIA");
        }
        return event.getRoomOB();
        
    }

    private final boolean exist(List<EventOB> events, EventOB event){
        int i = 0;
        if(events.isEmpty()){
            return false;
        }
        while(i <=event.getDate().toLocalDate().getDayOfMonth()){
            if(event.getDate() == events.get(i).getDate()
             && event.getRoomOB() == events.get(i).getRoomOB()){
                    return true; 
                }
            i++;
        }
        return false;
    }


}

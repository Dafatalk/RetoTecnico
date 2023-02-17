package com.example.demo.Service.usecase.usecaseimpl;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.Data.daofactory.DAOFactory;
import com.example.demo.Domain.EventOB;
import com.example.demo.Service.usecase.ListMontEventUsecase;

public class ListMontEventUsecaseImpl implements ListMontEventUsecase{
    private final DAOFactory factory;

    public ListMontEventUsecaseImpl(DAOFactory factory){
        this.factory=factory;

    }

    @Override
    public List<EventOB> execute(Integer month) {
        List<EventOB> allEvents = factory.getEventDAO().findEvents();
        List<EventOB> eventsInMonth = new ArrayList<>();
        
        for (EventOB event : allEvents) {
            if (event.getDate().toLocalDate().getMonthValue() == month) {
                eventsInMonth.add(event);
            }
        }
        
        return eventsInMonth;
    }
}

package com.example.demo.Service.usecase.usecaseimpl;

import java.util.List;

import com.example.demo.Data.daofactory.DAOFactory;
import com.example.demo.Domain.RoomOB;
import com.example.demo.Service.usecase.SearcRoomUsecase;

public class SearcRoomUsecaseImpl implements SearcRoomUsecase{
    private final DAOFactory factory;

    public SearcRoomUsecaseImpl(DAOFactory factory){
        this.factory=factory;

    }
    
    @Override
    public RoomOB execute(String nombre) {
        RoomOB result = new RoomOB();
        final RoomOB room = RoomOB.create(nombre);
        final List<RoomOB> results = factory.getRoomDAO().find(room);

        if(!results.isEmpty()){
            result = results.get(0);
        }
        return result;
    }
}

package com.example.demo.Domain;

import java.sql.Date;
import com.example.demo.Crosscutting.helper.StringHelper;
import static com.example.demo.Crosscutting.helper.DateHelper.getDeafultDate;
import static com.example.demo.Crosscutting.helper.StringHelper.EMPTY;

public class EventOB {
    
    private String name;
    private Date date;
    private RoomOB roomOB;

    public EventOB(){
        setDate(getDeafultDate());
        setRoomOB(new RoomOB());
        setName(StringHelper.EMPTY);
    }

    public EventOB(String nombre, Date fecha, RoomOB roomOB){
        setDate(fecha);
        setName(nombre);
        setRoomOB(roomOB);
    }
    
    public static final EventOB create(String nombre, Date fecha, RoomOB RoomOB){
        return new EventOB(nombre, fecha, RoomOB);
    }
    public static final EventOB create(Date fecha){
        return new EventOB(StringHelper.EMPTY, fecha, new RoomOB());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public RoomOB getRoomOB() {
        return roomOB;
    }

    public void setRoomOB(RoomOB roomOB) {
        this.roomOB = roomOB;
    }

    public boolean existe() {
        return !StringHelper.isDefaultString(EMPTY);
    }

}

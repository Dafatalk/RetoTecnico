package com.example.demo.Domain;

import static com.example.demo.Crosscutting.helper.StringHelper.EMPTY;
import com.example.demo.Crosscutting.helper.StringHelper;
public class RoomOB {
    
    private String name;
    private boolean Used = false;

    public RoomOB(){
        setUsed(false);
        setName(EMPTY);
    }
    


    public RoomOB(String nombre, boolean enUso) {
        setUsed(enUso);
        setName(nombre);
    }

    public static final RoomOB create(String name, boolean used){
        return new RoomOB(name, used);
    }
    public static final RoomOB create(String name){
        return new RoomOB(name, false);
    }



    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public boolean isUsed() {
        return Used;
    }



    public void setUsed(boolean used) {
        Used = used;
    }

    public  boolean exist(){
        return !StringHelper.isDefaultString(name);
    }


 
}

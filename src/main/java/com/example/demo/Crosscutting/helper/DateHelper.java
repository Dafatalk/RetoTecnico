package com.example.demo.Crosscutting.helper;

import java.sql.Date;
import java.time.LocalDate;

public class DateHelper {
    private static final LocalDate defaultDate = LocalDate.of(0001, 1, 1);

    private DateHelper() {
    }

    
    public  static final short getNextYear() {
        return  (short) LocalDate.now().plusYears(1).getYear();
    }

    public static final Date getDeafultDate() { 
        return Date.valueOf(defaultDate);
    }

    public static final Date getNow() {
        return Date.valueOf(LocalDate.now());
    }

    public static final boolean isDefaultDate(Date date){
        return Date.valueOf(defaultDate).equals(date);
    }

}

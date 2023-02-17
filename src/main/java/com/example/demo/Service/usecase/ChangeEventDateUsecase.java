package com.example.demo.Service.usecase;

import java.sql.Date;

import com.example.demo.Domain.EventOB;

public interface ChangeEventDateUsecase {
    void execute(EventOB event, Date fecha);
}

package com.example.demo.Service.usecase.command;

import java.sql.Date;

import com.example.demo.Domain.EventOB;

public interface ChangeEventDateCommand {
    void execute(EventOB event, Date date);
}

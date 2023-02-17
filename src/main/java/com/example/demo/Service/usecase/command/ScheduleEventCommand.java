package com.example.demo.Service.usecase.command;

import com.example.demo.Domain.EventOB;

public interface ScheduleEventCommand {
    void execute(EventOB event);
}

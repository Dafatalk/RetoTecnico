package com.example.demo.Service.usecase.command;

import java.util.List;

import com.example.demo.Domain.EventOB;

public interface ListMontEventCommand {
    List<EventOB> execute(Integer month);
}

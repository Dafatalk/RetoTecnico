package com.example.demo.Service.usecase;

import java.util.List;

import com.example.demo.Domain.EventOB;

public interface ListMontEventUsecase {
    List<EventOB> execute(Integer mes);
}

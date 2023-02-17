package com.example.demo.Data.dao;

import com.example.demo.Domain.EventOB;
import java.util.List;

public interface EventDAO {
    void create(EventOB evento);

	List<EventOB> find(EventOB evento);

	void update(EventOB evento);

	void delete(String nombre);

   List<EventOB> findEvents();
}

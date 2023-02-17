package com.example.demo.Data.dao;

import java.util.List;

import com.example.demo.Domain.RoomOB;

public interface RoomDAO {
    
    void create(RoomOB salon);

	List<RoomOB> find(RoomOB salon);

	void update(RoomOB salon);

	void delete(String nombre);

   List<RoomOB> findAllArtworkTypes();
}

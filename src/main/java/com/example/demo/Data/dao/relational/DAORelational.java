package com.example.demo.Data.dao.relational;

import java.sql.Connection;

import com.example.demo.Crosscutting.exception.data.DataCustomException;
import com.example.demo.Crosscutting.helper.SqlConnectionHelper;

public class DAORelational {
    
    private Connection connection;

    protected DAORelational (final Connection connection){

        if(!SqlConnectionHelper.connectionIsOpen(connection)){
            throw DataCustomException.CreateTechnicalException("LA CONEXION ESTÁ CERRADA");    
        }

        this.connection = connection;

    }

    protected final Connection getConnection(){
        return connection;
    }

    
}
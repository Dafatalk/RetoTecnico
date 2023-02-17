package com.example.demo.Data.daofactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.example.demo.Crosscutting.exception.crosscutting.CrosscuttingCustomException;
import com.example.demo.Crosscutting.exception.data.DataCustomException;
import com.example.demo.Crosscutting.helper.SqlConnectionHelper;
import com.example.demo.Data.dao.EventDAO;
import com.example.demo.Data.dao.RoomDAO;
import com.example.demo.Data.dao.relational.postgresql.EventPostgresqlDAO;
import com.example.demo.Data.dao.relational.postgresql.RoomPostgresqlDAO;

public class PostgreSQLDAOFactory extends DAOFactory{
    private Connection conexion;


    private String host = "jdbc:postgresql://localhost:5432/RETOJAVA";
    private String user = "postgres";
    private String password = "root";

    public PostgreSQLDAOFactory(){
        openConnection();
    }

    @Override
    protected void openConnection() {
        try {
            conexion = DriverManager.getConnection(host,user,password);
        } catch (SQLException exception) {
            throw DataCustomException.CreateTechnicalException("PROBLEMA PARA CONECTARSE A LA BASE DE DATOS", exception);
        }
    }


    @Override
    public void initTransaction() {
        try {
            SqlConnectionHelper.initTrasaction(conexion);
        } catch (CrosscuttingCustomException exception) {
            throw DataCustomException.CreateTechnicalException("PROBLEMA PARA INICIAR LA TRANSACCION", exception);
        }
    }

    @Override
    public void confirmTransaction() {
        try {
            SqlConnectionHelper.commitTrasaction(conexion);
        } catch (CrosscuttingCustomException exception) {
            throw DataCustomException.CreateTechnicalException("PROBLEMA PARA CONFIRMAR TRANSACCION", exception);
        }
    }

    @Override
    public void closeConection() {
        try {
            SqlConnectionHelper.closeConnection(conexion);
        } catch (CrosscuttingCustomException exception) {
            throw DataCustomException.CreateTechnicalException("PROBLEMA PARA CERRAR LA CONEXION", exception);
        }
    }

    @Override
    public void cancelTransaction() {
        try {
            SqlConnectionHelper.rollbackTrasaction(conexion);
        } catch (CrosscuttingCustomException exception) {
            throw DataCustomException.CreateTechnicalException("PROBLEMA PARA DEVOLVER LA TRANSACCION", exception);
        }
    }

    @Override
    public EventDAO getEventDAO() {
        return new EventPostgresqlDAO(conexion);
    }

    @Override
    public RoomDAO getRoomDAO() {
        return new RoomPostgresqlDAO(conexion);
    }

 
}

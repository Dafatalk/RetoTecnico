package com.example.demo.Data.daofactory;

import com.example.demo.Crosscutting.exception.data.DataCustomException;
import com.example.demo.Data.dao.EventDAO;
import com.example.demo.Data.dao.RoomDAO;
import com.example.demo.Data.enumeration.DAOFactoryType;

public abstract class DAOFactory {
    
    public static final DAOFactory getDAOFactory(DAOFactoryType factoria){
        DAOFactory factoriaDAO;
        
        switch(factoria){
            case SQLSERVER: 
                throw DataCustomException.CreateTechnicalException("SQLSERVER NO ESTA IMPLEMENTADO");        
            case ORACLE: 
                throw DataCustomException.CreateTechnicalException("ORACLE NO ESTA IMPLEMENTADO") ;
            case POSTGRESQL: 
                factoriaDAO = new PostgreSQLDAOFactory();
                break;
            default: 
                throw DataCustomException.CreateTechnicalException("PROBLEMA INESPERADO EN LA FACTORIADAO") ;
        }

        return factoriaDAO;
    }
    
    protected abstract void openConnection();

    public abstract void initTransaction();

    public abstract void confirmTransaction();

    public abstract void closeConection();
    
    public abstract void cancelTransaction();

    public abstract EventDAO getEventDAO();

    public abstract RoomDAO getRoomDAO();
}

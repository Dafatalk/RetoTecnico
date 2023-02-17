package com.example.demo.Crosscutting.helper;

import java.sql.Connection;
import java.sql.SQLException;

import com.example.demo.Crosscutting.exception.crosscutting.CrosscuttingCustomException;

public class SqlConnectionHelper {
        
    private SqlConnectionHelper(){
        super();
    } 

    public static final boolean connectionIsNull(final Connection connection){
        return ObjectHelper.isNull(connection);
    }

    public static final boolean connectionIsOpen(final Connection connection) {
        try {
            return !ObjectHelper.isNull(connection) && !connection.isClosed();
        } catch (final SQLException exception) {
            throw CrosscuttingCustomException.CreateTechnicalException("La conexión está cerrada", exception);
                    
        }
    }

    public static final void closeConnection(final Connection connection){
        try {
            if(!connectionIsOpen(connection)){
                throw CrosscuttingCustomException.CreateTechnicalException("La conexión ya está cerrada");
            }
            connection.close();
        } catch (final CrosscuttingCustomException exception) {
            throw exception;
        }catch(final SQLException exception) {
            throw CrosscuttingCustomException.CreateTechnicalException("Problemas cerrando la conexión", exception);
            
        }
    }

    public static final void initTrasaction(final Connection connection){
        try {
            if(!connectionIsOpen(connection)){
                throw CrosscuttingCustomException.CreateTechnicalException("la conexión está cerrada para iniciar la transacción");
            }
            connection.setAutoCommit(false);
        } catch (CrosscuttingCustomException exception) {
           throw exception;
        }catch (SQLException exception) {
            throw CrosscuttingCustomException.CreateTechnicalException("problema tratando de iniciar la transacción", exception);
        }
        
    }
    public static final void commitTrasaction(final Connection connection){
        try {
            if(!connectionIsOpen(connection)){
                throw CrosscuttingCustomException.CreateTechnicalException("la conexión está cerrada para confirmar la transacción");
            }
            connection.commit();
            connection.setAutoCommit(false);
        } catch (CrosscuttingCustomException exception) {
           throw exception;
        }catch (SQLException exception) {
            throw CrosscuttingCustomException.CreateTechnicalException("problemas tratando de iniciar la transacción", exception);
        }
        
    }
    public static final void rollbackTrasaction(final Connection connection){
        try {
            if(!connectionIsOpen(connection)){
                throw CrosscuttingCustomException.CreateTechnicalException("la conexión está cerrada para hacer rollback");
            }
            connection.setAutoCommit(false);
        } catch (CrosscuttingCustomException exception) {
           throw exception;
        }catch (SQLException exception) {
            throw CrosscuttingCustomException.CreateTechnicalException("problema trantando de hacer rollback", exception);
        }
        
    }

}

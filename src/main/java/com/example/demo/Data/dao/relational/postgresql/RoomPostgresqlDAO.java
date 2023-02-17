package com.example.demo.Data.dao.relational.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Crosscutting.exception.data.DataCustomException;
import com.example.demo.Crosscutting.helper.ObjectHelper;
import com.example.demo.Crosscutting.helper.StringHelper;
import com.example.demo.Data.dao.RoomDAO;
import com.example.demo.Data.dao.relational.DAORelational;
import com.example.demo.Domain.RoomOB;

public class RoomPostgresqlDAO extends DAORelational implements RoomDAO{
    
    public RoomPostgresqlDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void create(RoomOB room) {
        final var sql = "INSERT INTO public.room (name, used) VALUES (?, ?) ";

		try (final var preparedStatement = getConnection().prepareStatement(sql)) {
			
			preparedStatement.setString(1, room.getName());
			preparedStatement.setBoolean(2, room.isUsed());
			preparedStatement.executeUpdate();

		} catch (SQLException exception) {
			throw DataCustomException.CreateTechnicalException("PROBLEMA CREANDO EL SALON", exception); 
		} catch (Exception exception) {
			throw DataCustomException.CreateTechnicalException("PROBLEMA INESPERADO PARA ENCONTRAR EL SALON", exception); 
		}               
    }

    @Override
    public List<RoomOB> find(RoomOB room) {
		var parameters = new ArrayList<Object>();
		final var sqlBuilder = new StringBuilder();

		createSelectFrom(sqlBuilder);
		createWhere(sqlBuilder, room, parameters);

    		return prepareAndExecuteQuery(sqlBuilder, parameters);
    }

    @Override
    public void update(RoomOB room) {
		final var sql = "UPDATE public.room set name = ?, used = ?";

		try(final var prepareStatement = getConnection().prepareStatement(sql)) {
			
			prepareStatement.setString(1, room.getName());
			prepareStatement.setBoolean(2, room.isUsed());
			prepareStatement.executeUpdate();

		} catch (SQLException exception) {
			throw DataCustomException.CreateTechnicalException("PROBLEMA ACTUALIZANDO EL SALON", exception); 
		} catch (Exception exception) {
			throw DataCustomException.CreateTechnicalException("PROBLEMA INESPERADO ACTUALIZANDO EL SALON", exception); 
		}        
    }

    @Override
    public void delete(String name) {
        final var sql = "DELETE FROM public.room WHERE name = ?";
    
        try (final var preparedStatement = getConnection().prepareStatement(sql)) {
            
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            throw DataCustomException.CreateTechnicalException("PROBLEMA ELIMINANDO EL SALON", exception); 
        } catch (Exception exception) {
            throw DataCustomException.CreateTechnicalException("PROBLEMA INESPERADO ELIMINANDO EL SALON", exception); 
        }        
    }

    @Override
    public List<RoomOB> findAllArtworkTypes() {

        var parameters = new ArrayList<Object>();
        final var sqlBuilder = new StringBuilder();

        createSelectFrom(sqlBuilder);
        createOrderBy(sqlBuilder);
        return prepareAndExecuteQuery(sqlBuilder, parameters);  
    }
    
	private final void createSelectFrom(final StringBuilder sqlBuilder){
		sqlBuilder.append("select room.name, room.used ");
		sqlBuilder.append("from public.room ");
	}

	private final void createWhere(final StringBuilder sqBuilder, final RoomOB room, final List<Object> parameters){
		

		if(!ObjectHelper.isNull(room)){
			StringHelper.isDefaultString(room.getName());
				sqBuilder.append("WHERE name = ? ");
				parameters.add(room.getName());
			

		}
	}

	private final void createOrderBy(final StringBuilder sqlBuilder){

		sqlBuilder.append("ORDER BY room.name");

	}

    private final List<RoomOB> fillResults(final ResultSet resultSet){

        try{

            var results = new ArrayList<RoomOB>();

            while(resultSet.next()){

                results.add(fillRoomOB(resultSet));

            }

            return results;

        } catch (final SQLException exception){
            throw DataCustomException.CreateTechnicalException("PROBLEMA PARA LLENAR SALON", exception); 
        } catch (final Exception exception){
            throw DataCustomException.CreateTechnicalException("PROBLEMA INESPERADO PARA LLENAR SALON", exception); 
        }

    }

	private final RoomOB fillRoomOB(final ResultSet resultSet){

        try {

            return RoomOB.create(resultSet.getString("name"), resultSet.getBoolean("used") );

        } catch (SQLException exception) {
            throw DataCustomException.CreateTechnicalException("PROBLEMA TECNICO PARA ORGANIZAR LOS SALONES", exception); 
        }

    }
    private final List<RoomOB> prepareAndExecuteQuery(final StringBuilder sqlBuilder, final List<Object> parameters) {

        try (final var preparedStatement = getConnection().prepareStatement(sqlBuilder.toString())) {
            
            setParametersValues(preparedStatement, parameters);

            return executeQuery(preparedStatement);

        } catch (Exception exception) {
            throw DataCustomException.CreateTechnicalException("PROBLEMA EJECUTANDO EL QUERY", exception);
        }

    }


    private void setParametersValues(PreparedStatement preparedStatement, List<Object> parameters) {
        try {
            for(int index = 0; index < parameters.size(); index++){
                preparedStatement.setObject(index + 1, parameters.get(index));
            }
        } catch(final SQLException exception){
            throw DataCustomException.CreateTechnicalException("PROBLEMA DANDO LOS PARAMETROS", exception); 
        } catch(final Exception exception) {
            throw DataCustomException.CreateTechnicalException("PROBLEMA INESPERADO DANDO LOS PARAMETROS", exception); 
        }
    }

    private final List<RoomOB> executeQuery(PreparedStatement preparedStatement){

        try (final var resultSet = preparedStatement.executeQuery()) {
            return fillResults(resultSet);
        } catch(final SQLException exception){
            throw DataCustomException.CreateTechnicalException("PROBLEMA EJECTUANDO EL QUERY", exception); 
        } catch(final DataCustomException exception) {
            throw exception;
        } catch(final Exception exception){
            throw DataCustomException.CreateTechnicalException("PROBLEMA INESPERADO CON EL QUERY", exception); 
        }
    }

}

package com.example.demo.Data.dao.relational.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Crosscutting.exception.data.DataCustomException;
import com.example.demo.Crosscutting.helper.DateHelper;
import com.example.demo.Crosscutting.helper.ObjectHelper;
import com.example.demo.Crosscutting.helper.StringHelper;
import com.example.demo.Data.dao.EventDAO;
import com.example.demo.Data.dao.relational.DAORelational;
import com.example.demo.Domain.EventOB;
import com.example.demo.Domain.RoomOB;

public class EventPostgresqlDAO extends DAORelational implements EventDAO {

    public EventPostgresqlDAO(Connection connection) {
        super(connection);

    }

    @Override
    public void create(EventOB event) {
        final var sql = "INSERT INTO public.event(name, room_name, date) VALUES (?, ?, ?)";

		try (final var preparedStatement = getConnection().prepareStatement(sql)) {
			
			preparedStatement.setString(1, event.getName());           
            preparedStatement.setString(2, event.getRoomOB().getName());
			preparedStatement.setDate(3, event.getDate());


			preparedStatement.executeUpdate();

		} catch (SQLException exception) {
			throw DataCustomException.CreateTechnicalException("PROBLEMA CREANDO EL EVENTO", exception); 
		} catch (Exception exception) {
			throw DataCustomException.CreateTechnicalException("PROBLEMA INESPERADO PARA ENCONTRAR EL ERROR", exception); 
		}        
    }

    @Override
    public List<EventOB> find(EventOB event) {
		var parameters = new ArrayList<Object>();
		final var sqlBuilder = new StringBuilder();

		createSelectFrom(sqlBuilder);
		createWhere(sqlBuilder, event, parameters);
		createOrderBy(sqlBuilder);

		return prepareAndExecuteQuery(sqlBuilder, parameters);
    }

    @Override
    public void update(EventOB event) {
		final var sql = "UPDATE public.event set name = ?, room_name = ?, date = ?";

		try(final var prepareStatement = getConnection().prepareStatement(sql)) {
			
			prepareStatement.setString(1, event.getName());           
            prepareStatement.setString(2, event.getRoomOB().getName());
			prepareStatement.setDate(3, event.getDate());

			prepareStatement.executeUpdate();

		} catch (SQLException exception) {
			throw DataCustomException.CreateTechnicalException("PROBLEMA ACTUALIZANDO EL EVENTO", exception); 
		} catch (Exception exception) {
			throw DataCustomException.CreateTechnicalException("PROBLEMA INESPERADO ACTUALIZANDO EL EVENTO", exception); 
		}
    }

    @Override
    public void delete(String name) {
            final var sql = "DELETE FROM public.event WHERE name = ?";
    
            try (final var preparedStatement = getConnection().prepareStatement(sql)) {
                
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();
    
            } catch (SQLException exception) {
                throw DataCustomException.CreateTechnicalException("PROBLEMA ELIMINANDO EL EVENTO", exception); 
            } catch (Exception exception) {
                throw DataCustomException.CreateTechnicalException("PROBLEMA INESPERADO ELIMINANDO EL EVENTO", exception); 
            }
        
    }


    @Override
    public List<EventOB> findEvents() {
    
        var parameters = new ArrayList<Object>();
        final var sqlBuilder = new StringBuilder();

        createSelectFrom(sqlBuilder);
        createOrderBy(sqlBuilder);
        return prepareAndExecuteQuery(sqlBuilder, parameters);
    }
    
	private final void createSelectFrom(final StringBuilder sqlBuilder){
		sqlBuilder.append("select event.name, event.room_name, event.date ");
		sqlBuilder.append("from public.event ");
	}

	private final void createWhere(final StringBuilder sqBuilder, final EventOB event, final List<Object> parameters){
		
		var setWhere = true;

		if(!ObjectHelper.isNull(event)){

			if(!StringHelper.isDefaultString(event.getName())){
				sqBuilder.append("WHERE name = ? ");
				setWhere = false;
				parameters.add(event.getName());
			}
			if(!DateHelper.isDefaultDate(event.getDate())){
				sqBuilder.append(setWhere ? "WHERE " : "AND ").append("date = ? ");
				parameters.add(event.getDate());
			}
            if(!StringHelper.isDefaultString(event.getRoomOB().getName())){
                sqBuilder.append(setWhere ? "WHERE " : "AND ").append("room_name = ? ");
				parameters.add(event.getRoomOB().getName());
            }

		}
	}

	private final void createOrderBy(final StringBuilder sqlBuilder){

		sqlBuilder.append("ORDER BY event.name");

	}

    private final List<EventOB> fillResults(final ResultSet resultSet){

        try{

            var results = new ArrayList<EventOB>();

            while(resultSet.next()){

                results.add(fillEventoOB(resultSet));

            }

            return results;

        } catch (final SQLException exception){
            throw DataCustomException.CreateTechnicalException("PROBLEMA PARA LLENAR EVENTOS", exception); 
        } catch (final Exception exception){
            throw DataCustomException.CreateTechnicalException("PROBLEMA INESPERADO PARA LLENAR EVENTOS", exception); 
        }

    }
    private final EventOB fillEventoOB(final ResultSet resultSet){

        try {

            return EventOB.create(resultSet.getString("name"), 
                                     resultSet.getDate("date"),
                                     fillRoomOB(resultSet));
            
        } catch (SQLException exception) {
            throw DataCustomException.CreateTechnicalException("PROBLEMA LLENANDO EL EVENTO", exception); 
        }

    }

	private final RoomOB fillRoomOB(final ResultSet resultSet){

        try {

            return RoomOB.create(resultSet.getString("name"));

        } catch (SQLException exception) {
            throw DataCustomException.CreateTechnicalException("PROBLEMA TECNICO PARA LLENAR LOS SALONES", exception); 
        }

    }

    private final List<EventOB> prepareAndExecuteQuery(final StringBuilder sqlBuilder, final List<Object> parameters) {

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

    private final List<EventOB> executeQuery(PreparedStatement preparedStatement){

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

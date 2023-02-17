package com.example.demo.API.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.API.controller.response.Response;
import com.example.demo.Crosscutting.exception.RetoCustomException;
import com.example.demo.Domain.EventOB;
import com.example.demo.Service.usecase.command.CancelEventCommand;
import com.example.demo.Service.usecase.command.ChangeEventDateCommand;
import com.example.demo.Service.usecase.command.ListMontEventCommand;
import com.example.demo.Service.usecase.command.ScheduleEventCommand;
import com.example.demo.Service.usecase.command.commandimpl.CancelEventCommandImpl;
import com.example.demo.Service.usecase.command.commandimpl.ChangeEventDateCommandImpl;
import com.example.demo.Service.usecase.command.commandimpl.ListMontEventCommandImpl;
import com.example.demo.Service.usecase.command.commandimpl.ScheduleEventCommandImpl;

@RestController
@RequestMapping("/palmas/event")
public class EventController {
    private ScheduleEventCommand scheduleEvent = new ScheduleEventCommandImpl();
    private ListMontEventCommand listMonth = new ListMontEventCommandImpl();
    private CancelEventCommand cancelEvent = new CancelEventCommandImpl();
    private ChangeEventDateCommand changeDate = new ChangeEventDateCommandImpl();

    @GetMapping
    public EventOB showEvent(){
        return new EventOB();
    }

	@PostMapping("/agendar")
    public ResponseEntity<Response<EventOB>> agendar(@RequestBody EventOB event){
		Response<EventOB> response = new Response<>();
		HttpStatus httpStatus = HttpStatus.OK;
		try {
				scheduleEvent.execute(event);
				final List<EventOB> data = new ArrayList<>();
				data.add(event);
				response.setData(data);
				response.addSuccessMessage("Se ha creado el evento Correctamente");
			
		}catch(final RetoCustomException exception) {
			httpStatus = HttpStatus.BAD_REQUEST;

			if(exception.isTechinalException()) {
				response.addErrorMessage("Problema Tecnico Creando el evento");
			}else {
				response.addErrorMessage(exception.getMessage());
			}	
			exception.printStackTrace();
		}catch(final Exception exception) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.addFatalMessage("problema Inesperado creando el evento, llorelo");
			exception.printStackTrace();

		}
			
		return new ResponseEntity<>(response,httpStatus);
    }
    
    @GetMapping("/listar")
    public ResponseEntity<Response<EventOB>> listar(@RequestParam Integer month){
		Response<EventOB> response = new Response<>();
		HttpStatus httpStatus = HttpStatus.OK;
		try {
        		final List<EventOB> data = listMonth.execute(month);
				response.setData(data);
				response.addSuccessMessage("Se han encontrado los eventos correctamente");
			
		}catch(final RetoCustomException exception) {
			httpStatus = HttpStatus.BAD_REQUEST;

			if(exception.isTechinalException()) {
				response.addErrorMessage("Problema Tecnico buscando el evento");
			}else {
				response.addErrorMessage(exception.getMessage());
			}	
			exception.printStackTrace();
		}catch(final Exception exception) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.addFatalMessage("problema Inesperado buscando el evento, llorelo");
			exception.printStackTrace();

		}
			
		return new ResponseEntity<>(response,httpStatus);
    }

    @PostMapping("/cancelar")
    public ResponseEntity<Response<EventOB>> cancelar(@RequestBody EventOB event){
		Response<EventOB> response = new Response<>();
		HttpStatus httpStatus = HttpStatus.OK;
		try {
			cancelEvent.execute(event);
			final List<EventOB> data = new ArrayList<>();
			data.add(event);
			response.setData(data);	
			response.addSuccessMessage("Se ha cancelado el evento correctamente");
			
		}catch(final RetoCustomException exception) {
			httpStatus = HttpStatus.BAD_REQUEST;

			if(exception.isTechinalException()) {
				response.addErrorMessage("Problema Tecnico cancelando el evento");
			}else {
				response.addErrorMessage(exception.getMessage());
			}	
			exception.printStackTrace();
		}catch(final Exception exception) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.addFatalMessage("problema Inesperado cancelando el evento, llorelo");
			exception.printStackTrace();

		}
			
		return new ResponseEntity<>(response,httpStatus);
    }
    @PostMapping("/cambiar")
    public ResponseEntity<Response<EventOB>> cambiar(@RequestBody EventOB event, Date date){
		Response<EventOB> response = new Response<>();
		HttpStatus httpStatus = HttpStatus.OK;
		try {
				changeDate.execute(event, date);
				response.addSuccessMessage("Se ha modificado la fecha del evento Correctamente");
			
		}catch(final RetoCustomException exception) {
			httpStatus = HttpStatus.BAD_REQUEST;

			if(exception.isTechinalException()) {
				response.addErrorMessage("Problema Tecnico modificando la fecha del evento");
			}else {
				response.addErrorMessage(exception.getMessage());
			}	
			exception.printStackTrace();
		}catch(final Exception exception) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			response.addFatalMessage("problema Inesperado modificando la fecha del evento, llorelo");
			exception.printStackTrace();

		}
        return new ResponseEntity<>(response,httpStatus);


    
}
}


package com.codingchallenge.campsite.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.codingchallenge.campsite.rest.exceptions.DateParsingException;
import com.codingchallenge.campsite.rest.exceptions.IllegalDateException;
import com.codingchallenge.campsite.rest.exceptions.IllegalRequestException;
import com.codingchallenge.campsite.rest.exceptions.IncompleteRequestException;
import com.codingchallenge.campsite.rest.exceptions.ReservationNotFoundException;


@ControllerAdvice
public class ExceptionHelper {
	
	   private static final Logger log = LoggerFactory.getLogger(ExceptionHelper.class);

	       @ExceptionHandler(value = {DateParsingException.class })
	       public ResponseEntity<Object> handleInvalidInputException(DateParsingException ex) {
	   
	    	   log.error("Invalid Input Exception: ",ex.getMessage());
	   
	           return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	   
	       }
	       
	       @ExceptionHandler(value = {IllegalDateException.class })
	       public ResponseEntity<Object> handleInvalidInputException(IllegalDateException ex) {
	   
	    	   log.error("Invalid Input Exception: ",ex.getMessage());
	   
	           return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	   
	       }
	       
	       @ExceptionHandler(value = {IncompleteRequestException.class })
	       public ResponseEntity<Object> handleInvalidInputException(IncompleteRequestException ex) {
	   
	    	   log.error("Invalid Input Exception: ",ex.getMessage());
	   
	           return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	   
	       }	       
	       
	       @ExceptionHandler(value = {ReservationNotFoundException.class })
	       public ResponseEntity<Object> handleInvalidInputException(ReservationNotFoundException ex) {
	   
	    	   log.error("Invalid Input Exception: ",ex.getMessage());
	   
	           return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.NOT_FOUND);
	   
	       }	
	       
	       @ExceptionHandler(value = {IllegalRequestException.class })
	       public ResponseEntity<Object> handleInvalidInputException(IllegalRequestException ex) {
	   
	    	   log.error("Invalid Input Exception: ",ex.getMessage());
	   
	           return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	   
	       }	
	       
	       @ExceptionHandler(value = {org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class })
	       public ResponseEntity<Object> handleInvalidInputException(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex) {
	   
	    	   log.error("Invalid Input Exception: ",ex.getMessage());
	   
	           return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	   
	       }	
	       
	       
	       
	       
	       
	       

}

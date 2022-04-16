package com.codingchallenge.campsite.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ReservationNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4669470606382840101L;
	
	private static String HELP_MESSAGE = "";
	public ReservationNotFoundException() {
        super();
    }
    public ReservationNotFoundException(String message, Throwable cause) {
        super(message + HELP_MESSAGE, cause);
    }
    public ReservationNotFoundException(String message) {
        super(message + HELP_MESSAGE);
    }
    public ReservationNotFoundException(Throwable cause) {
        super(cause);
    }
}
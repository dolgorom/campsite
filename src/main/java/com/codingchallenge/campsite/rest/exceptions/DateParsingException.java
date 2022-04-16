package com.codingchallenge.campsite.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DateParsingException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4669470606382840101L;
	
	private static String HELP_MESSAGE = " Date should be in format YYYY-MM-DD";
	public DateParsingException() {
        super();
    }
    public DateParsingException(String message, Throwable cause) {
        super(message + HELP_MESSAGE, cause);
    }
    public DateParsingException(String message) {
        super(message + HELP_MESSAGE);
    }
    public DateParsingException(Throwable cause) {
        super(cause);
    }
}
package com.codingchallenge.campsite.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncompleteRequestException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4669470606382840101L;
	
	private static String HELP_MESSAGE = "";
	public IncompleteRequestException() {
        super();
    }
    public IncompleteRequestException(String message, Throwable cause) {
        super(message + HELP_MESSAGE, cause);
    }
    public IncompleteRequestException(String message) {
        super(message + HELP_MESSAGE);
    }
    public IncompleteRequestException(Throwable cause) {
        super(cause);
    }
}
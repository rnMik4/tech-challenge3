package com.restaurantes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.io.Serializable;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DatabaseException extends  RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 7771981395049112822L;

    public DatabaseException(final String message) {
        super(message);
    }
}

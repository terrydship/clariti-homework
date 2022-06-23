package com.clariti.homework.fee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Terry Deng
 */

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FeeException extends RuntimeException {
    public FeeException(String message) {
        super(message);
    }
}

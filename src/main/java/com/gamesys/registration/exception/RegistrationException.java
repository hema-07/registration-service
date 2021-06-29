package com.gamesys.registration.exception;

import com.gamesys.registration.modal.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationException extends Exception {

    private static final long serialVersionId = 1L;
    private ErrorResponse errorResponse;
    private HttpStatus httpStatus;

    public RegistrationException(String exception) { super(exception);}
}

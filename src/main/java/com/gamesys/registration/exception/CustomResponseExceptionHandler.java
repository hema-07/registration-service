package com.gamesys.registration.exception;

import com.gamesys.registration.modal.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.JDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import static com.gamesys.registration.util.Constants.*;

@ControllerAdvice
@RestController
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LogManager.getLogger(CustomResponseExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().orElse(ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(invalidRequestParam)
                .errorDescription(invalidRequestParamDetails+errorMsg)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMsg = ex.getMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(servletBindingError)
                .errorDescription(servletBindingErrorDetails+errorMsg)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value= {JDBCException.class, JDBCConnectionException.class})
    public final ResponseEntity<Object> handleEJDBCException(JDBCException ex, WebRequest request) {
        String errorMsg = ex.getMessage();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(jdbcException)
                .errorDescription(jdbcExceptionDetails+errorMsg)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


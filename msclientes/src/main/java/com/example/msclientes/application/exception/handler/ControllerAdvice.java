package com.example.msclientes.application.exception.handler;

import com.example.msclientes.application.exception.exceptiondto.ExceptionDto;
import com.example.msclientes.application.exception.exceptions.ViolacaoDeIntegridadeException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ViolacaoDeIntegridadeException.class)
    public ResponseEntity<ExceptionDto> dadosClientesNotFound(ViolacaoDeIntegridadeException exception, HttpServletRequest request) {

        ExceptionDto ex = new ExceptionDto(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
    }

}

package com.example.msavaliadorcredito.application.exception.handler;

import com.example.msavaliadorcredito.application.exception.exception.dto.ExceptionDto;
import com.example.msavaliadorcredito.application.exception.exceptions.ConexaoRecusadaException;
import com.example.msavaliadorcredito.application.exception.exceptions.DadosClienteNotFoundException;
import com.example.msavaliadorcredito.application.exception.exceptions.ErroComunicacaoMicroservicesException;
import com.example.msavaliadorcredito.application.exception.exceptions.ErroSolicitacaoCartaoException;
import com.example.msavaliadorcredito.application.exception.exceptions.ServicoIndisponivelException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(DadosClienteNotFoundException.class)
    public ResponseEntity<ExceptionDto> dadosClientesNotFound(DadosClienteNotFoundException exception, HttpServletRequest request) {

        ExceptionDto ex = new ExceptionDto(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex);
    }

    @ExceptionHandler(ErroComunicacaoMicroservicesException.class)
    public ResponseEntity<ExceptionDto> erroComunicacaoMicroservices(ErroComunicacaoMicroservicesException exception, HttpServletRequest request) {

        ExceptionDto ex = new ExceptionDto(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex);
    }

    @ExceptionHandler(ConexaoRecusadaException.class)
    public ResponseEntity<ExceptionDto> conexaoRecusada(ConexaoRecusadaException exception, HttpServletRequest request) {

        ExceptionDto ex = new ExceptionDto(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex);
    }

    @ExceptionHandler(ServicoIndisponivelException.class)
    public ResponseEntity<ExceptionDto> servicoIndisponivel(ServicoIndisponivelException exception, HttpServletRequest request) {

        ExceptionDto ex = new ExceptionDto(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex);
    }


    @ExceptionHandler(ErroSolicitacaoCartaoException.class)
    public ResponseEntity<ExceptionDto> erroSolicitacaoCartaoException(ErroSolicitacaoCartaoException exception, HttpServletRequest request) {

        ExceptionDto ex = new ExceptionDto(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex);
    }

}

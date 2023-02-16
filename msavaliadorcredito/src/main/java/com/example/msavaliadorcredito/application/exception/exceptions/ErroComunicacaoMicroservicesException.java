package com.example.msavaliadorcredito.application.exception.exceptions;

import lombok.Getter;

public class ErroComunicacaoMicroservicesException extends RuntimeException{

    public ErroComunicacaoMicroservicesException(String message) {
        super(message);
    }

}

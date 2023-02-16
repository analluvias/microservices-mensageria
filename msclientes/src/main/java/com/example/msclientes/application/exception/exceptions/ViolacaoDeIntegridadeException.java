package com.example.msclientes.application.exception.exceptions;

public class ViolacaoDeIntegridadeException extends RuntimeException{

    public ViolacaoDeIntegridadeException() {
        super("Verifique se os seus dados estão" +
                " corretos ou se você já possui uma conta em nosso serviço");
    }

}

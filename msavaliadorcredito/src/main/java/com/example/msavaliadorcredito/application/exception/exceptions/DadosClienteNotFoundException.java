package com.example.msavaliadorcredito.application.exception.exceptions;

public class DadosClienteNotFoundException extends RuntimeException{

    public DadosClienteNotFoundException() {
        super("Dados do cliente não encontrados para o cpf informado");
    }
}

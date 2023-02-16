package com.example.msavaliadorcredito.domain.model;

import lombok.Data;

@Data // essa classe vai conversar com o ms de cliente
public class DadosCliente {

    private Long id;

    private String nome;

    private Integer idade;

}

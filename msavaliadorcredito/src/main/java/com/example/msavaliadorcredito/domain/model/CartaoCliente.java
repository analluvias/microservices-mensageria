package com.example.msavaliadorcredito.domain.model;

import java.math.BigDecimal;
import lombok.Data;

@Data // representa o retorno dos cartões que o cliente tem -> conversa com o ms de cartao -> recebe o dto CartoesPorClienteResponse
public class CartaoCliente {

    private String nome;

    private String bandeira;

    private BigDecimal limiteLiberado;
}

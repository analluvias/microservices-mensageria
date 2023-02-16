package com.example.mscartoes.domain;

import java.math.BigDecimal;
import lombok.Data;

// classe para guardar o json que eu recebo ao solicitar um cartao
@Data
public class DadosSolicitacaoEmissaoCartao {

    private Long idCartao;

    private String cpf;

    private String endereco;

    private BigDecimal limiteLiberado;
}
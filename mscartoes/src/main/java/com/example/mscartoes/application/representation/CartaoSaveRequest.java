package com.example.mscartoes.application.representation;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartaoSaveRequest {
    private String nome;
    private String bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBasico;
}

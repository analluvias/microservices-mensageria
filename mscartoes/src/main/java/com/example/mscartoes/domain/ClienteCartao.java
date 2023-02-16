package com.example.mscartoes.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class ClienteCartao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String cpf;

    //muitos clientes podem possuir o mesmo modelo de cartao
    @ManyToOne
    @JoinColumn(name = "id_cartao")
    private Cartao cartao;
    //mas possuem um limite personalizado de acordo com a renda
    // passada na solicitação
    @Column
    private BigDecimal limite;
}

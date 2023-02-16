package com.example.msclientes.application.representation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteSaveRequest {
    private String cpf;
    private String nome;
    private Integer idade;

}

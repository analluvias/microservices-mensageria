package com.example.msavaliadorcredito.infra.clients;

import com.example.msavaliadorcredito.domain.model.DadosCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//classe que será cliente da api de clientes, via comunicação http
// value = "nome mapeado no yaml do ms de clientes"

@FeignClient( value = "msclientes", path = "/clientes")
public interface ClienteResourceClient {

    // colocar assinatura do método que eu quero conversar (está no controller do ms de clientes)
    @GetMapping(params = "cpf")
    public ResponseEntity<DadosCliente> dadosDoCliente(@RequestParam String cpf);

}

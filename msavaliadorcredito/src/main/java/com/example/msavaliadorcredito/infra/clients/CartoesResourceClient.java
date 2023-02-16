package com.example.msavaliadorcredito.infra.clients;

import com.example.msavaliadorcredito.domain.model.Cartao;
import com.example.msavaliadorcredito.domain.model.CartaoCliente;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// fazendo comunicação com o microsserviço de cartões

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartoesResourceClient {

    // assinatura do método (no controller de mscartoes) com que eu quero me comunicar
    // mudamos só o tipo de ResponseEntity<List<CartoesPorClienteResponse>>
    // para ResponseEntity<List<CartaoCliente>> que é o dto que eu tenho aqui
    // mas eu poderia mudar o nome de CartaoCliente para CartoesPorClienteResponse

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@RequestParam String cpf);

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda );


}

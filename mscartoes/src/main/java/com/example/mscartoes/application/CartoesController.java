package com.example.mscartoes.application;

import com.example.mscartoes.application.representation.CartaoSaveRequest;
import com.example.mscartoes.application.representation.CartoesPorClienteResponse;
import com.example.mscartoes.domain.Cartao;
import com.example.mscartoes.domain.ClienteCartao;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartoesController {

    private final ModelMapper modelMapper;

    private final CartaoService cartaoService;

    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request){

        cartaoService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    //VAI BUSCAR OS CARTOES QUE ACEITEM UMA RENDA MENOR OU IGUAL A RENDA QUE O CLIENTE ENVIE
    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda ){

        return ResponseEntity.ok(  cartaoService.getCartaoRendaMenorIgual(renda)  );

    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>>
    getCartoesByCliente(@RequestParam String cpf){

        return ResponseEntity.ok(  clienteCartaoService.listCartaoByCpf(cpf)  );

    }
}

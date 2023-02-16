package com.example.msclientes.application;

import com.example.msclientes.application.representation.ClienteSaveRequest;
import com.example.msclientes.domain.Cliente;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Slf4j //permique eu eu faça logs
public class ClientesController {

    private final ModelMapper modelMapper;

    private final ClienteService service;

    @GetMapping
    public String status(){
        log.info("Obtendo o status do microservice de clientes");
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest request){
        Cliente cliente = modelMapper.map(request, Cliente.class);

        service.save(cliente);

        // construindo uma url dinâmica através da url corrente
        // será mais ou menos: http://localhost:PORT/clientes?cpf=01234567890
        // que é o link pelo qual eu conseguirei obter o GET desse cliente criado agora
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();

        //response entity com o header location -> nova url
        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity dadosDoCliente(@RequestParam String cpf){
        var cliente = service.getByCPF(cpf);

        if (cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }
}



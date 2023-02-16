package com.example.mscartoes.application;

import com.example.mscartoes.application.representation.CartoesPorClienteResponse;
import com.example.mscartoes.domain.ClienteCartao;
import com.example.mscartoes.infra.repository.ClienteCartaoRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository repository;

    public List<CartoesPorClienteResponse> listCartaoByCpf(String cpf){

        List<ClienteCartao> listaCliente = repository.findByCpf(cpf);

        List<CartoesPorClienteResponse> cartoesPorClienteResponseList = listaCliente.stream()
                .map(CartoesPorClienteResponse::fromModel)
                .collect(Collectors.toList());

        return cartoesPorClienteResponseList;
    }
}

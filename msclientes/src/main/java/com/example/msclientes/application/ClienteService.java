package com.example.msclientes.application;

import com.example.msclientes.application.exception.exceptions.ViolacaoDeIntegridadeException;
import com.example.msclientes.domain.Cliente;
import com.example.msclientes.infra.repository.ClienteRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // construtor com atributos final
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public Cliente save(Cliente cliente){
        try {

            return repository.save(cliente);

        }catch (org.springframework.dao.DataIntegrityViolationException e){
            throw new ViolacaoDeIntegridadeException();
        }
    }

    public Optional<Cliente> getByCPF(String cpf){
        return repository.findByCpf(cpf);
    }
}

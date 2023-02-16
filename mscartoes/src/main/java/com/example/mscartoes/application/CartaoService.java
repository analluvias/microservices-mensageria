package com.example.mscartoes.application;

import com.example.mscartoes.application.representation.CartaoSaveRequest;
import com.example.mscartoes.domain.Cartao;
import com.example.mscartoes.infra.repository.CartaoRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final ModelMapper modelMapper;

    private final CartaoRepository repository;

    @Transactional
    public Cartao save (CartaoSaveRequest request){
        Cartao cartao = modelMapper.map(request, Cartao.class);
        return repository.save(cartao);
    }

    public List<Cartao> getCartaoRendaMenorIgual(Long renda){
        var rendaBigDecimal = BigDecimal.valueOf(renda);

        return repository.findByRendaLessThanEqual(rendaBigDecimal);
    }
}

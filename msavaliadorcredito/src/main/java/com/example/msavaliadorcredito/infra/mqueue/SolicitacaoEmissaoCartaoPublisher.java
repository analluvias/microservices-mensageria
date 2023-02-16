package com.example.msavaliadorcredito.infra.mqueue;

import com.example.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitacaoEmissaoCartaoPublisher {

    // classe que irá enviar as mensagens para a fila

    private final RabbitTemplate rabbitTemplate;

    // criei essa fila dentro de config.MQConfig
    private final Queue queueEmissaoCartoes;

    public void solicitarCartao(  DadosSolicitacaoEmissaoCartao dados  ) throws JsonProcessingException {

        var json = convertIntoJson(  dados  );

        // enviando para a fila queueEmissaoCartoes o json que eu criei
        rabbitTemplate.convertAndSend(queueEmissaoCartoes.getName(), json);

    }

    // método que converte de string em json
    private String convertIntoJson(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(dados);

        return json;
    }
}

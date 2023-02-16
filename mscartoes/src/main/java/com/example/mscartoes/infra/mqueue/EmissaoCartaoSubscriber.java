package com.example.mscartoes.infra.mqueue;

import com.example.mscartoes.domain.Cartao;
import com.example.mscartoes.domain.ClienteCartao;
import com.example.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import com.example.mscartoes.infra.repository.CartaoRepository;
import com.example.mscartoes.infra.repository.ClienteCartaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;

    private final ClienteCartaoRepository clienteCartaoRepository;

    // 1. precisa do starter spring-boot-starter-amqp
    // 2. anotar a classe application com @EnableRabbit
    // 3. colocar no yml: mq:queues:emissao-cartoes: emissao-cartoes
    // 4. também congfigurar a porta e o acesso ao rabbitmq -> no yml
    // depois criar essa classe, que irá de fato se inscrever em uma fila

    // esse ms vai ficar escutando a fila de emissao-cartoes
    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload){
        try {

            // mapper para desserializar o json que vou receber
            ObjectMapper mapper = new ObjectMapper();

            //desserializando para o tipo DadosSolicitacaoEmissaoCartao
            DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);

            Cartao cartao = cartaoRepository.findById(dados.getIdCartao())
                    .orElseThrow();


            // criando o cliente a partir do cartao
            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao( cartao );
            clienteCartao.setCpf( dados.getCpf() );
            clienteCartao.setLimite( dados.getLimiteLiberado() );

            // salvando o cliente que tem um cartao
            clienteCartaoRepository.save(clienteCartao);

        }catch (Exception e){
            log.error("Erro ao receber solicitação de emissão de cartao: {}", e.getMessage());
        }


    }

}

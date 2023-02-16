package com.example.msavaliadorcredito.application;

import com.example.msavaliadorcredito.application.exception.exceptions.ConexaoRecusadaException;
import com.example.msavaliadorcredito.application.exception.exceptions.DadosClienteNotFoundException;
import com.example.msavaliadorcredito.application.exception.exceptions.ErroComunicacaoMicroservicesException;
import com.example.msavaliadorcredito.application.exception.exceptions.ErroSolicitacaoCartaoException;
import com.example.msavaliadorcredito.application.exception.exceptions.ServicoIndisponivelException;
import com.example.msavaliadorcredito.domain.model.Cartao;
import com.example.msavaliadorcredito.domain.model.CartaoAprovado;
import com.example.msavaliadorcredito.domain.model.CartaoCliente;
import com.example.msavaliadorcredito.domain.model.DadosCliente;
import com.example.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import com.example.msavaliadorcredito.domain.model.ProtocoloSolicitacaoCartao;
import com.example.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import com.example.msavaliadorcredito.domain.model.SituacaoCliente;
import com.example.msavaliadorcredito.infra.clients.CartoesResourceClient;
import com.example.msavaliadorcredito.infra.clients.ClienteResourceClient;
import com.example.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import feign.FeignException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    // para fazer comunicação entre os microsserviços utilizaremos uma biblioteca nova
    // OpenFeign → comunicação síncrona e direta

    // comunicacao com o ms de clientes
    private final ClienteResourceClient clientesClient;

    //comunicacao com o ms de cartoes
    private final CartoesResourceClient cartoesClient;

    // isso aqui só é injetado no final, depois que o ms está funcionando
    // para que ele acesse a fila do RabittMq
    private final SolicitacaoEmissaoCartaoPublisher solicitacaoEmissaoCartaoPublisher;


    // obtem os dados do cliente — MSCLIENTES -> CONTROLLER - dadosCliente
    // obtem cartoes desse cliente - MSCARTOES -> CONTROLLER - getCartoesByCliente
    public SituacaoCliente obterSituacaoCliente(String cpf) {

        try {

            ResponseEntity<DadosCliente> dadosDoCliente = clientesClient.dadosDoCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesByCliente = cartoesClient.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(  dadosDoCliente.getBody()  )
                    .cartoes(  cartoesByCliente.getBody()  )
                    .build();

        }catch (feign.RetryableException e){

            throw new ConexaoRecusadaException(e.getMessage());

        }
        catch (FeignException.FeignClientException e){

            int status = e.status();

            // só retorna 404 se não tiver o cliente
            // pois a api de cartões sempre retorna um lista, mesmo que vazia
            if (HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicacaoMicroservicesException(  e.getMessage()  );

        }catch (feign.FeignException.ServiceUnavailable e){
            throw new ServicoIndisponivelException(e.getMessage());
        }
    }

    // pegando os dados do cliente -> no ms de cliente
    // pegando os cartoes para os que possuem renda até tal valor -> ms de cartoes
    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda){
        try {

            ResponseEntity<DadosCliente> dadosDoCliente = clientesClient.dadosDoCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAteh(renda);

            List<Cartao> cartaoList = cartoesResponse.getBody();

            // calculando o limite individual aprovado nos cartoes a que ele tem direito
            List<CartaoAprovado> cartoesAprovadosList = cartaoList.stream().map(cartao -> {

                //lógica para definir o limite do cartão
                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBigDecimal = BigDecimal.valueOf(dadosDoCliente.getBody().getIdade());
                BigDecimal fator = idadeBigDecimal.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setNome(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);

                return aprovado;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(cartoesAprovadosList);

        }catch (feign.RetryableException e){

            throw new ConexaoRecusadaException(e.getMessage());

        }
        catch (FeignException.FeignClientException e){

            int status = e.status();

            // só retorna 404 se não tiver o cliente
            // pois a api de cartões sempre retorna um lista, mesmo que vazia
            if (HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }

            throw new ErroComunicacaoMicroservicesException(  e.getMessage()  );

        }catch (feign.FeignException.ServiceUnavailable e){
            throw new ServicoIndisponivelException(e.getMessage());
        }
    }

    // método que chama o publisher para enviar os dados para o ms de cartoes
    // e retorna para o cliente um código único que significa o numero de
    // protocolo daquele pedido
    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados){
        try {

            solicitacaoEmissaoCartaoPublisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();

            return new ProtocoloSolicitacaoCartao(protocolo);

        }catch (Exception e){

            throw new ErroSolicitacaoCartaoException(e.getMessage());

        }
    }
}

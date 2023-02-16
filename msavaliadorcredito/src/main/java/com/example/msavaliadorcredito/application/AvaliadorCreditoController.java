package com.example.msavaliadorcredito.application;

import com.example.msavaliadorcredito.application.exception.exceptions.ErroSolicitacaoCartaoException;
import com.example.msavaliadorcredito.domain.model.DadosAvaliacao;
import com.example.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import com.example.msavaliadorcredito.domain.model.ProtocoloSolicitacaoCartao;
import com.example.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import com.example.msavaliadorcredito.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/avaliacoes-credito")
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status(){
        return "ok";
    }

    //localhost:8080/avaliacoes-credito/situacao-cliente?cpf=
    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf){

        SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
        return ResponseEntity.ok(  situacaoCliente  );

    }

    @PostMapping
    public ResponseEntity realizarAvaliacao(  @RequestBody DadosAvaliacao dados ){

        RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService
                .realizarAvaliacao(dados.getCpf(), dados.getRenda());

        return ResponseEntity.ok(retornoAvaliacaoCliente);

    }

    // caminho para solicitar o meu cartao
    @PostMapping("/solicitacoes-cartao")
    public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados){

        ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = avaliadorCreditoService
                .solicitarEmissaoCartao(dados);

        return ResponseEntity.ok(protocoloSolicitacaoCartao);

    }

}

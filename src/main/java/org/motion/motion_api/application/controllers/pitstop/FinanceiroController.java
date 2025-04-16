package org.motion.motion_api.application.controllers.pitstop;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.motion.motion_api.domain.dtos.pitstop.financeiro.CreateFinanceiroDTO;
import org.motion.motion_api.domain.dtos.pitstop.financeiro.ResponseDataFinanceiro;
import org.motion.motion_api.application.services.FinanceiroService;
import org.motion.motion_api.domain.dtos.pitstop.financeiro.ResponseDataUltimoAnoFinanceiroDTO;
import org.motion.motion_api.domain.dtos.pitstop.financeiro.UpdateFinanceiroDTO;
import org.motion.motion_api.domain.entities.pitstop.Financeiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financeiro")
@SecurityRequirement(name = "motion_jwt")
public class FinanceiroController {
    @Autowired
    private FinanceiroService financeiroService;

    //crie o @Operation que defina o método abaixo e se puder cria o de todos os métodos dessa classe também
    @Operation(summary = "Lista as informações financeiras de uma oficina, saldo, valor de entradas, valor de saídas. DO MÊS ATUAL.")
    @GetMapping("/info-mes/{id}")
    public ResponseEntity<ResponseDataFinanceiro> listarInformacaoByIdOficina(@PathVariable int id) {
        ResponseDataFinanceiro info = financeiroService.listarDadosFinanceirosDoMes(id);
        return ResponseEntity.status(200).body(info);
    }

    @Operation(summary = "Registra uma transação financeira.")
    @PostMapping()
    public ResponseEntity<Financeiro> registrarTransacaoFinanceira(@RequestBody @Valid CreateFinanceiroDTO dto) {
        Financeiro financeiro = financeiroService.registrarTransacaoFinanceira(dto);
        return ResponseEntity.status(201).body(financeiro);
    }

    @Operation(summary = "Lista todas as operações financeiras de uma oficina.")
    @GetMapping("/oficina/{idOficina}")
    public ResponseEntity<List<Financeiro>> listarTodasOperacoesFinanceiras(@PathVariable int idOficina) {
        List<Financeiro> financas = financeiroService.listarTodasOperacoesFinanceiras(idOficina);
        return ResponseEntity.status(200).body(financas);
    }

    @Operation(summary = "Recebe os dados e um id de uma operação financeira e atualiza a operação financeira.")
    @PutMapping("/atualiza/{id}")
    public ResponseEntity<Financeiro> atualizar(@PathVariable int id, @Valid @RequestBody UpdateFinanceiroDTO dto) {
        Financeiro financeiro = financeiroService.atualizarFinanceiro(id,dto);
        return ResponseEntity.status(200).body(financeiro);
    }

    @Operation(summary = "Deleta uma operação financeira.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFinanceiro(@PathVariable int id) {
        financeiroService.deletarFinanceiro(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Retorna o resumo das informações financeiras dos últimos 12 meses de uma oficina")
    @GetMapping("/info-12-meses/{id}")
    public ResponseEntity<List<ResponseDataUltimoAnoFinanceiroDTO>> listarInformacao12MesesByIdOficina(@PathVariable int id) {
        List<ResponseDataUltimoAnoFinanceiroDTO> info = financeiroService.listarDadosFinanceirosDosUltimos12Meses(id);
        return ResponseEntity.status(200).body(info);
    }
}

package org.motion.motion_api.application.controllers.pitstop;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.motion.motion_api.domain.dtos.pitstop.ordemDeServico.*;
import org.motion.motion_api.application.services.OrdemDeServicoService;
import org.motion.motion_api.domain.entities.pitstop.OrdemDeServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ordemDeServicos")
@SecurityRequirement(name = "motion_jwt")
public class OrdemDeServicoController {

    @Autowired
    OrdemDeServicoService ordemDeServicoService;

    @Operation(summary = "Listar ordens de serviço")
    @GetMapping()
    public ResponseEntity<List<OrdemCriadaDTO>> listarOrdensDeServico(){
        return ResponseEntity.status(200).body(ordemDeServicoService.listarOrdensDeServico());
    }

    @Operation(summary = "Listar ordens de serviço por id da oficina")
    @GetMapping("/oficina/{idOficina}")
    public ResponseEntity<List<OrdemCriadaDTO>> listarOrdensDeServicoPorOficina(@PathVariable Integer idOficina){
        List<OrdemCriadaDTO> ordemDeServicos = ordemDeServicoService.listarOrdensDeServicoPorOficina(idOficina);
        if(ordemDeServicos.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(ordemDeServicos);
    }

    @Operation(summary = "Listar ordens de serviço por email do cliente")
    @GetMapping("/cliente/{email}")
    public ResponseEntity<List<OrdemCriadaDTO>> listarOrdensDeServicoPorCliente(@PathVariable String email){
        List<OrdemCriadaDTO> ordemDeServicos = ordemDeServicoService.listarOrdensDeServicoPorCliente(email);
        if(ordemDeServicos.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(ordemDeServicos);
    }

    @Operation(summary = "Cadastrar ordem de serviço")
    @PostMapping()
    public ResponseEntity<OrdemCriadaDTO> cadastrar(@RequestBody @Valid CreateOrdemDeServicoDTO novaOrdemDeServicoDTO){
        return ResponseEntity.status(201).body(ordemDeServicoService.cadastrar(novaOrdemDeServicoDTO));
    }

    @Operation(summary = "Atualizar ordem de serviço")
    @PutMapping("/{id}")
    public ResponseEntity<OrdemCriadaDTO> atualizar(@PathVariable Integer id, @RequestParam String status){
        return ResponseEntity.ok(ordemDeServicoService.atualizarStatus(id, status));
    }

    @Operation(summary = "Buscar ordem de serviço por id")
    @GetMapping("/{id}")
    public ResponseEntity<OrdemCriadaDTO> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(ordemDeServicoService.buscarPorId(id));
    }

    @Operation(summary = "Deletar ordem de serviço por id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        ordemDeServicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar ordem de serviço por token")
    @GetMapping("/token/{token}")
    public ResponseEntity<OrdemCriadaDTO> buscarPorToken(@PathVariable String token){
        return ResponseEntity.ok(ordemDeServicoService.buscarPorToken(token));
    }

    @Operation(summary = "Recebe o id da oficina e retorna a quantidade de ordens pendentes na ultima semana, o respectivo dia e a quantidade do dia")
    @GetMapping("/quantidade-pendentes/{idOficina}")
    public ResponseEntity<List<OrdensPendentesUltimaSemanaDTO>> quantidadeOrdensPendentes(@PathVariable Integer idOficina){
        return ResponseEntity.ok(ordemDeServicoService.quantidadeOrdensPendentes(idOficina));
    }

    @Operation(summary = "Retorna a quantidade de ordens de serviço de cada mês nos ultimos 12 meses, recebe o id da oficina")
    @GetMapping("/quantidade-mes/{idOficina}")
    public ResponseEntity<List<OrdensUltimoAnoDTO>> quantidadeOrdensMes(@PathVariable Integer idOficina){
        return ResponseEntity.ok(ordemDeServicoService.quantidadeOrdensMes(idOficina));
    }

}

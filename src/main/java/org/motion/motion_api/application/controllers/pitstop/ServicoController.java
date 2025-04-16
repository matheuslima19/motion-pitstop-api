package org.motion.motion_api.application.controllers.pitstop;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.motion.motion_api.domain.dtos.pitstop.servico.CreateServicoDTO;
import org.motion.motion_api.application.services.ServicoService;
import org.motion.motion_api.domain.dtos.pitstop.servico.ServicoResponseDTO;
import org.motion.motion_api.domain.dtos.pitstop.servico.UpdateServicoDTO;
import org.motion.motion_api.domain.entities.pitstop.ProdutoEstoque;
import org.motion.motion_api.domain.entities.pitstop.Servico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
@SecurityRequirement(name = "motion_jwt")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @Operation(summary = "Retorna todos os serviços cadastrados.")
    @GetMapping()
    public ResponseEntity<List<ServicoResponseDTO>> listarTodos() {
        return ResponseEntity.status(200).body(servicoService.listarServicos());
    }

    @Operation(summary = "Busca um serviço por id")
    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> buscarPorId(@PathVariable int id) {
        ServicoResponseDTO servico = servicoService.buscarPorId(id);
        return ResponseEntity.ok(servico);
    }

    @Operation(summary = "Cadastra um serviço")
    @PostMapping()
    public ResponseEntity<Servico> cadastrar(@RequestBody @Valid CreateServicoDTO createServicoDTO){
        Servico servico = servicoService.cadastrar(createServicoDTO);
        return ResponseEntity.status(201).body(servico);
    }

    @Operation(summary = "Deleta um serviço por id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza um serviço por id")
    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizar(@PathVariable int id, @RequestBody @Valid UpdateServicoDTO servico){
        Servico servicoAtualizado = servicoService.atualizar(id, servico);
        return ResponseEntity.ok(servicoAtualizado);
    }


    @Operation(summary = "Busca os serviços da oficina, recebe o id da oficina.")
    @GetMapping("/oficina/{idOficina}")
    public ResponseEntity<List<ServicoResponseDTO>> buscarPorOficina(@PathVariable int idOficina){
        List<ServicoResponseDTO> servicos = servicoService.buscarPorOficina(idOficina);
        return ResponseEntity.ok(servicos);
    }
}

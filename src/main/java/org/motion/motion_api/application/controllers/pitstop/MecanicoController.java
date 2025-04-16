package org.motion.motion_api.application.controllers.pitstop;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.motion.motion_api.domain.dtos.pitstop.mecanico.CreateMecanicoDTO;
import org.motion.motion_api.application.services.MecanicoService;
import org.motion.motion_api.domain.dtos.pitstop.mecanico.UpdateMecanicoDTO;
import org.motion.motion_api.domain.entities.pitstop.Mecanico;
import org.motion.motion_api.domain.entities.pitstop.OrdemDeServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mecanicos")
@SecurityRequirement(name = "motion_jwt")
public class MecanicoController {

    @Autowired
    private MecanicoService mecanicoService;

    @Operation(summary = "Retorna todos os mecânicos cadastrados.")
    @GetMapping()
    public ResponseEntity<List<Mecanico>> listarTodos() {
        return ResponseEntity.status(200).body(mecanicoService.listarMecanicos());
    }

    @Operation(summary = "Listar mecanicos id da oficina")
    @GetMapping("/oficina/{idOficina}")
    public ResponseEntity<List<Mecanico>> listarMecanicosPorOficina(@PathVariable Integer idOficina){
        List<Mecanico> mecanicos = mecanicoService.listarMecanicosPorOficina(idOficina);
        if(mecanicos.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(mecanicos);
    }

    @Operation(summary = "Cadastra um mecânico")
    @PostMapping()
    public ResponseEntity<Mecanico> cadastrar(@RequestBody @Valid CreateMecanicoDTO createMecanicoDTO){
        Mecanico mecanico = mecanicoService.cadastrar(createMecanicoDTO);
        return ResponseEntity.status(201).body(mecanico);
    }

    @Operation(summary = "Busca um mecânico por id")
    @GetMapping("/{id}")
    public ResponseEntity<Mecanico> buscarPorId(@PathVariable int id) {
        Mecanico mecanico = mecanicoService.buscarPorId(id);
        return ResponseEntity.ok(mecanico);
    }

    @Operation(summary = "Deleta um mecânico por id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        mecanicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza um mecânico por id")
    @PutMapping("/{id}")
    public ResponseEntity<Mecanico> atualizar(@PathVariable int id, @RequestBody UpdateMecanicoDTO mecanico){
        Mecanico mecanicoAtualizado = mecanicoService.atualizar(id, mecanico);
        return ResponseEntity.ok(mecanicoAtualizado);
    }

    @Operation(summary = "Busca um mecânico por nome")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Mecanico> buscarPorNome(@PathVariable String nome){
        Mecanico mecanico = mecanicoService.buscarPorNome(nome);
        return ResponseEntity.ok(mecanico);
    }

    @Operation(summary = "Busca um mecânico por nome e id")
    @GetMapping("/nome/{nome}/id/{id}")
    public ResponseEntity<Mecanico> buscarPorNomeEId(@PathVariable String nome, @PathVariable Integer id){
        Mecanico mecanico = mecanicoService.buscarPorNomeEId(nome, id);
        return ResponseEntity.ok(mecanico);
    }
}

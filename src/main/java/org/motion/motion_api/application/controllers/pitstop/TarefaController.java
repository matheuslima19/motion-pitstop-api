package org.motion.motion_api.application.controllers.pitstop;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.motion.motion_api.domain.dtos.pitstop.tarefa.CreateTarefaDTO;
import org.motion.motion_api.domain.dtos.pitstop.tarefa.UpdateTarefaDTO;
import org.motion.motion_api.application.services.TarefaService;
import org.motion.motion_api.domain.entities.pitstop.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
@SecurityRequirement(name = "motion_jwt")
public class TarefaController {
    @Autowired
    private TarefaService tarefaService;


    @Operation(summary = "Busca todas as tarefas, recebe o id da oficina buscada.")
    @GetMapping("/{idOficina}")
    public ResponseEntity<List<Tarefa>> listarTodasTarefasPorIdOficina(@PathVariable int idOficina) {
        List<Tarefa> tarefas = tarefaService.listarTodasTarefasPorIdOficina(idOficina);
        return tarefas.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(tarefas);
    }

    @Operation(summary = "Cadastra uma tarefa")
    @PostMapping()
    public ResponseEntity<Tarefa> criar(@RequestBody CreateTarefaDTO createTarefaDTO) {
        return ResponseEntity.status(201).body(tarefaService.criar(createTarefaDTO));
    }


    @Operation(summary = "Atualiza o status de uma tarefa")
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@RequestBody UpdateTarefaDTO updateTarefaDTO, @PathVariable int id) {
        return ResponseEntity.status(200).body(tarefaService.atualizar(id, updateTarefaDTO));
    }

    @Operation(summary = "Busca tarefas que a data da deadline seja hoje, recebe o id da oficina buscada.")
    @GetMapping("/deadline-hoje/{idOficina}")
    public ResponseEntity<List<Tarefa>> listarTarefasDeadlineHoje(@PathVariable int idOficina) {
        List<Tarefa> tarefas = tarefaService.listarTarefasDeadlineHoje(idOficina);
        return tarefas.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(tarefas);
    }

    @Operation(summary = "Deleta uma tarefa recebe o id da tarefa")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

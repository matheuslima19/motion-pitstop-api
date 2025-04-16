package org.motion.motion_api.application.controllers.pitstop;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.motion.motion_api.domain.dtos.pitstop.veiculo.CreateVeiculoDTO;
import org.motion.motion_api.application.services.VeiculoService;
import org.motion.motion_api.domain.entities.pitstop.Veiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
@SecurityRequirement(name = "motion_jwt")
public class VeiculoController {
    @Autowired
    private VeiculoService veiculoService;

    @GetMapping()
    public ResponseEntity<List<Veiculo>> listarTodos(){
        List<Veiculo> veiculos = veiculoService.listarVeiculos();
        if(veiculos.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(veiculos);
    }

    @PostMapping()
    public ResponseEntity<Veiculo> cadastrar(@RequestBody @Valid CreateVeiculoDTO novoVeiculoDTO){
        Veiculo veiculo = veiculoService.cadastrar(novoVeiculoDTO);
        return ResponseEntity.status(201).body(veiculo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        veiculoService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> atualizar(@PathVariable int id, @RequestBody @Valid CreateVeiculoDTO novoVeiculoDTO){
        Veiculo veiculo = veiculoService.atualizar(id,novoVeiculoDTO);
        return ResponseEntity.status(200).body(veiculo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> buscarPorId(@PathVariable int id){
        Veiculo veiculo = veiculoService.buscarPorId(id);
        return ResponseEntity.status(200).body(veiculo);
    }

    @GetMapping("/buscar-por-cliente/{idCliente}")
    public ResponseEntity<List<Veiculo>> buscarPorCliente(@PathVariable int idCliente){
        List<Veiculo> veiculos = veiculoService.buscarPorIdCliente(idCliente);
        if(veiculos.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(veiculos);
    }

    @GetMapping("/buscar-por-oficina/{idOficina}")
    public ResponseEntity<List<Veiculo>> buscarPorOficina(@PathVariable int idOficina){
        List<Veiculo> veiculos = veiculoService.buscarPorIdOficina(idOficina);
        if(veiculos.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(veiculos);
    }
}

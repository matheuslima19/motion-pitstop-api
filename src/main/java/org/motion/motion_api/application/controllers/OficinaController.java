package org.motion.motion_api.application.controllers;

import io.swagger.v3.oas.annotations.Operation;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.motion.motion_api.domain.dtos.oficina.UpdateLogoOficinaDTO;
import org.motion.motion_api.domain.dtos.oficina.UpdateOficinaDTO;
import org.motion.motion_api.application.services.OficinaService;
import org.motion.motion_api.domain.entities.Oficina;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/oficinas")
@SecurityRequirement(name = "motion_jwt")
@CrossOrigin(origins = "*")
public class OficinaController {

    @Autowired
    private OficinaService oficinaService;


    @Operation(summary = "Busca todas oficinas")
    @GetMapping
    public ResponseEntity<List<Oficina>> listarOficinas() {
        List<Oficina> oficinas = oficinaService.listarTodos();
        if (oficinas.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.ok(oficinas);
    }


    @Operation(summary = "Busca uma oficina por id")
    @GetMapping("/{id}")
    public ResponseEntity<Oficina> buscarOficinaPorId(@PathVariable Integer id) {
        Oficina oficina = oficinaService.buscarPorId(id);
        return ResponseEntity.ok(oficina);
    }

    @Operation(summary = "Busca oficinas por tipo de veículo recebendo um parametro tipo que pode ser, carro, moto etc")
    @GetMapping("/tipo-veiculo")
    public ResponseEntity<List<Oficina>> buscarOficinasPorTipoVeiculo(@RequestParam(required = false, defaultValue = "") String tipo) {
        List<Oficina> oficinas = oficinaService.buscarPorTipoVeiculo(tipo);
        if (oficinas.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.ok(oficinas);
    }

    @Operation(summary = "Busca oficinas por tipo de veiculo, propulsão, marca e nome recebe os quatro parametros")
    @GetMapping("/tipo-veiculo-propulsao-marca-nome")
    public ResponseEntity<List<Oficina>> buscarOficinasPorTipoVeiculoPropulsaoMarca(
            @RequestParam(required = false, defaultValue = "") String tipoVeiculo,
            @RequestParam(required = false, defaultValue = "") String tipoPropulsao,
            @RequestParam(required = false, defaultValue = "") String marca,
            @RequestParam(required = false, defaultValue = "") String nome
    ) {
        List<Oficina> oficinas = oficinaService.buscarPorTipoVeiculoPropulsaoMarcaNome(tipoVeiculo, tipoPropulsao, marca,nome);
        if (oficinas.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.ok(oficinas);
    }

    @Operation(summary = "Cadastra uma oficina")
    @PostMapping
    public ResponseEntity<Oficina> criarOficina(@RequestBody @Valid Oficina oficina) {
        Oficina novaOficina = oficinaService.criar(oficina);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaOficina);
    }

    @Operation(summary = "Atualiza os dados de uma oficina")
    @PutMapping("/{id}")
    public ResponseEntity<Oficina> atualizarOficina(@PathVariable Integer id, @Valid @RequestBody UpdateOficinaDTO oficinaAtualizada) {
        Oficina oficina = oficinaService.atualizar(id, oficinaAtualizada);
        return ResponseEntity.ok(oficina);
    }

    @Operation(summary = "Atualiza a logo de uma oficina")
    @PutMapping("/atualiza-foto/{id}")
    public ResponseEntity<Oficina> atualizarFotoOficina(@PathVariable Integer id, @Valid @RequestBody UpdateLogoOficinaDTO dto) {
        Oficina oficina = oficinaService.atualizarLogoUrl(id, dto);
        return ResponseEntity.ok(oficina);
    }

    @Operation(summary = "Deleta uma oficina e seus registros")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOficina(@PathVariable Integer id) {
        // return ResponseEntity.status(501).build();
        oficinaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

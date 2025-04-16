package org.motion.motion_api.application.controllers.pitstop;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.motion.motion_api.application.services.FotoGaleriaOficinaService;
import org.motion.motion_api.domain.dtos.oficina.SetFotoOficinaDTO;
import org.motion.motion_api.domain.entities.pitstop.FotoGaleriaOficina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/galerias")
@SecurityRequirement(name = "motion_jwt")
public class FotoOficinaGaleriaController {
    @Autowired
    private FotoGaleriaOficinaService galeriaOficinaService;

    @GetMapping("/oficina/{id}")
    @Operation(summary = "Recebe o id de uma oficina e retorna todas as fotos da oficina caso ela exista.")
    public ResponseEntity<List<FotoGaleriaOficina>> buscarFotosOficina(@PathVariable int id) {
        List<FotoGaleriaOficina> fotos = galeriaOficinaService.buscarFotosOficina(id);
        if(fotos.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(fotos);
    }

    @PostMapping("/oficina/{id}")
    @Operation(summary = "Recebe o id de uma oficina e uma foto e salva a foto na galeria da oficina.")
    public ResponseEntity<FotoGaleriaOficina> salvarFotoOficina(@PathVariable int id, @RequestBody @Valid SetFotoOficinaDTO dto) {
        FotoGaleriaOficina fotoSalva = galeriaOficinaService.salvarFotoOficina(id, dto);
        return ResponseEntity.status(201).body(fotoSalva);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Recebe o id de uma foto e a deleta.")
    public ResponseEntity<Void> deletarFoto(@PathVariable int id) {
        galeriaOficinaService.deletarFoto(id);
        return ResponseEntity.noContent().build();
    }
}

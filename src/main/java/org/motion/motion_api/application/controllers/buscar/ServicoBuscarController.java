package org.motion.motion_api.application.controllers.buscar;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.motion.motion_api.application.services.ServicoBuscarService;
import org.motion.motion_api.domain.dtos.buscar.CreateServicoBuscarDTO;
import org.motion.motion_api.domain.entities.buscar.ServicoBuscar;
import org.motion.motion_api.domain.entities.pitstop.Servico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buscar-servicos")
@SecurityRequirement(name = "motion_jwt")
public class ServicoBuscarController {

    @Autowired
    private ServicoBuscarService servicoBuscarService;


    @GetMapping()
    public ResponseEntity<List<ServicoBuscar>> listarServicos() {
        return ResponseEntity.ok(servicoBuscarService.listarServicos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoBuscar> buscarServicoPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicoBuscarService.buscarServicoPorId(id));
    }

    @Operation(summary = "Busca buscar-serviços com o filtro de tipoVeiculo que a oficina atende")
    @GetMapping(value = "/tipo-veiculo-nome")
    public ResponseEntity<List<ServicoBuscar>> buscarPorTipoVeiculo(
            @RequestParam(required = false, defaultValue = "") String tipoVeiculo,
            @RequestParam(required = false, defaultValue = "") String nome
            ){
        List<ServicoBuscar> servicos = servicoBuscarService.buscarPorTipoVeiculo(tipoVeiculo,nome);
        System.out.println(tipoVeiculo);
        if(servicos.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.ok(servicos);
    }

    @PostMapping()
    public ResponseEntity<ServicoBuscar> cadastrar(@RequestBody @Valid CreateServicoBuscarDTO novoServicoBuscar) {
        return ResponseEntity.ok(servicoBuscarService.cadastrar(novoServicoBuscar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Integer id) {
        servicoBuscarService.deletarServico(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca os serviços que serão exibidos no buscar recebe o id da oficina")
    @GetMapping("/oficina/{idOficina}")
    public ResponseEntity<List<ServicoBuscar>> buscarPorOficina(@PathVariable int idOficina){
        List<ServicoBuscar> servicos = servicoBuscarService.buscarPorOficina(idOficina);
        if(servicos.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.ok(servicos);
    }
}

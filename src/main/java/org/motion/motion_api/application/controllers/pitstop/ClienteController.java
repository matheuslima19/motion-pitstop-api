package org.motion.motion_api.application.controllers.pitstop;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.motion.motion_api.domain.dtos.pitstop.cliente.CreateClienteDTO;
import org.motion.motion_api.application.services.ClienteService;
import org.motion.motion_api.domain.entities.pitstop.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@SecurityRequirement(name = "motion_jwt")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping()
    public ResponseEntity<List<Cliente>> listarTodos(){
        return ResponseEntity.status(200).body(clienteService.listarClientes());
    }

    @Operation(summary = "Recebe um id de oficina e retorna todos os clientes que pertencem a essa oficina")
    @GetMapping("/oficina/{id}")
    public ResponseEntity<List<Cliente>> listarPorOficina(@PathVariable int id){
        List<Cliente> clientes = clienteService.listarPorOficina(id);
        if(clientes.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(clientes);
    }

    @PostMapping()
    public ResponseEntity<Cliente> cadastrar(@Valid @RequestBody CreateClienteDTO novoCliente){
        Cliente cliente = clienteService.cadastrar(novoCliente);
        return ResponseEntity.status(201).body(cliente);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        clienteService.deletar(id);
        return ResponseEntity.status(204).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable int id, @RequestBody CreateClienteDTO novoCliente){
        Cliente cliente = clienteService.atualizar(id,novoCliente);
        return ResponseEntity.status(200).body(cliente);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable int id){
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.status(200).body(cliente);
    }

    @Operation(summary = "Recebe um parâmetro e retorna todos os clientes que satisfazem esse parâmetro em algum campo (nome,email ou telefone)")
    @GetMapping("/filtro")
    public ResponseEntity<List<Cliente>> buscaPorFiltro(@RequestParam String param){
        List<Cliente> clientes = clienteService.buscaPorFiltro(param);
        if(clientes.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(clientes);
    }
}

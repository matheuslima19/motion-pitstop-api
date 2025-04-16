package org.motion.motion_api.application.controllers.pitstop;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.motion.motion_api.application.services.GerenteService;
import org.motion.motion_api.domain.dtos.pitstop.gerente.*;
import org.motion.motion_api.domain.entities.pitstop.Gerente;
import org.motion.motion_api.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gerentes")
@SecurityRequirement(name = "motion_jwt")
public class GerenteController {

    @Autowired
    private GerenteService gerenteService;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Retorna todos os gerentes cadastrados." ,security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping()
    public ResponseEntity<List<Gerente>> listarTodos() {
        return ResponseEntity.status(200).body(gerenteService.listarTodos());
    }

    @Operation(summary = "Busca um gerente por id")
    @GetMapping("/{id}")
    public ResponseEntity<Gerente> buscarPorId(@PathVariable int id) {
        Gerente gerente = gerenteService.buscarPorId(id);
        return ResponseEntity.ok(gerente);
    }

    @Operation(summary = "Retorna um gerente a partir de um email e senha")
    @PostMapping("/login") @PermitAll
    public ResponseEntity<LoginGerenteResponse> login(@RequestBody @Valid LoginGerenteRequest request) {
        LoginGerenteResponse response = gerenteService.login(request);
        return ResponseEntity.status(200).body(response);
    }


    @Operation(summary = "Cadastra um gerente")
    @PostMapping()@PermitAll
    public ResponseEntity<Gerente> cadastrar(@RequestBody @Valid CreateGerenteDTO createGerenteDTO) throws MessagingException {
        Gerente gerente = gerenteService.criar(createGerenteDTO);
        return ResponseEntity.status(201).body(gerente);
    }


    @Operation(summary = "Atualiza o nome e o sobrenome do gerente")
    @PutMapping("{id}")
    public ResponseEntity<Gerente> atualizar(@PathVariable int id, @RequestBody UpdateGerenteDTO updateGerenteDTO) {
        Gerente gerente = gerenteService.atualizar(id, updateGerenteDTO);
        return ResponseEntity.status(200).body(gerente);
    }

    @Operation(summary = "Atualiza a senha do gerente")
    @PutMapping("/atualiza-senha/{id}")
    public ResponseEntity<Gerente> atualizarSenha(@PathVariable int id, @RequestBody UpdateSenhaGerenteDTO updateSenhaGerenteDTO) {
        Gerente gerente = gerenteService.atualizarSenha(id, updateSenhaGerenteDTO);
        return ResponseEntity.status(200).body(gerente);
    }

    @Operation(summary = "Seta um token de um gerente pelo email e envia o email com o token. Recebe um parâmetro op que pode ser email ou senha para a respectiva operação")
    @PostMapping("/set-token")
    public ResponseEntity<Void> setToken(@RequestBody @Valid SendEmailDTO dto, @RequestParam String op) throws MessagingException {
        gerenteService.enviarTokenConfirmacao(dto.getEmail(),op);
        return ResponseEntity.status(204).build();
    }
    @Operation(summary = "Confirma o token de um gerente, após a confirmação o token é removido. Recebe um parâmetro op que pode ser email ou senha para a respectiva operação. Caso a operação seja confirmação de email não é necessário colocar a nova senha")
    @PostMapping("/confirmar-token")
    public ResponseEntity<Void> confirmarToken(@RequestBody @Valid ConfirmTokenDTO dto, @RequestParam String op) {
        gerenteService.validarTokenConfirmacao(dto,op);
        return ResponseEntity.status(204).build();
    }


    @Operation(summary = "Atualiza a a url da foto do gerente")
    @PutMapping("/atualiza-foto/{id}")
    public ResponseEntity<Gerente> atualizarFoto(@PathVariable int id, @RequestBody @Valid UpdateFotoGerenteDTO updateFotoGerenteDTO) {
        Gerente gerente = gerenteService.atualizarUrlFoto(id, updateFotoGerenteDTO);
        return ResponseEntity.status(200).body(gerente);
    }
    @Operation(summary = "Deleta um gerente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        gerenteService.deletar(id);
        return ResponseEntity.status(204).build();
    }
}

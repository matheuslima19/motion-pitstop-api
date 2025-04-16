package org.motion.motion_api.application.controllers.pitstop;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.motion.motion_api.domain.dtos.pitstop.produtoEstoque.CreateProdutoEstoqueDTO;
import org.motion.motion_api.application.services.ProdutoEstoqueService;
import org.motion.motion_api.domain.dtos.pitstop.produtoEstoque.ProdutoEstoqueResponseDTO;
import org.motion.motion_api.domain.dtos.pitstop.produtoEstoque.UpdateProdutoEstoqueDTO;
import org.motion.motion_api.domain.entities.pitstop.ProdutoEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtoEstoque")
@SecurityRequirement(name = "motion_jwt")
public class ProdutoEstoqueController {
    @Autowired
    private ProdutoEstoqueService produtoEstoqueService;

    @Operation(summary = "Retorna todos os produtos de estoque cadastrados.")
    @GetMapping()
    public ResponseEntity<List<ProdutoEstoqueResponseDTO>> listarTodos() {
        return ResponseEntity.status(200).body(produtoEstoqueService.listarProdutosEstoque());
    }

    @Operation(summary = "Retorna todos os produtos que encaixam em uma faixa de preço", description = "Recebe os parâmetros de preço mínimo e máximo e caso não receba os valores o default é 0 e 99999 respectivamente.")
    @GetMapping("/preco-nome")
    public ResponseEntity<List<ProdutoEstoque>> listarPorPreco(
            @RequestParam(required = false,defaultValue = "0") double precoMinimo,
            @RequestParam(required = false, defaultValue = "99999") double precoMaximo,
            @RequestParam(required = false, defaultValue = "") String nome
            ){
        List<ProdutoEstoque> produtos = produtoEstoqueService.listarPorPreco(precoMinimo, precoMaximo,nome);
        if(produtos.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.ok(produtos);
    }

    @Operation(summary = "Cadastra um produto de estoque")
    @PostMapping()
    public ResponseEntity<ProdutoEstoque> cadastrar(@RequestBody @Valid CreateProdutoEstoqueDTO createProdutoEstoqueDTO){
        ProdutoEstoque produtoEstoque = produtoEstoqueService.cadastrar(createProdutoEstoqueDTO);
        return ResponseEntity.status(201).body(produtoEstoque);
    }

    @Operation(summary = "Busca um produto de estoque por id")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoEstoqueResponseDTO> buscarPorId(@PathVariable int id) {
        ProdutoEstoqueResponseDTO produtoEstoque = produtoEstoqueService.buscarPorId(id);
        return ResponseEntity.ok(produtoEstoque);
    }

    @Operation(summary = "Deleta um produto de estoque por id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        produtoEstoqueService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza um produto de estoque por id")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoEstoque> atualizar(@PathVariable int id, @RequestBody @Valid UpdateProdutoEstoqueDTO produtoEstoque){
        ProdutoEstoque produtoEstoqueAtualizado = produtoEstoqueService.atualizar(id, produtoEstoque);
        return ResponseEntity.ok(produtoEstoqueAtualizado);
    }

    @Operation(summary = "Busca um produto de estoque por nome")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<ProdutoEstoqueResponseDTO> buscarPorNome(@PathVariable String nome){
        ProdutoEstoqueResponseDTO produtoEstoque = produtoEstoqueService.buscarPorNome(nome);
        return ResponseEntity.ok(produtoEstoque);
    }

    @Operation(summary = "Busca todos os produtos do estoque de uma oficina")
    @GetMapping("/oficina/{idOficina}")
    public ResponseEntity<List<ProdutoEstoqueResponseDTO>> buscarPorOficina(@PathVariable int idOficina){
        List<ProdutoEstoqueResponseDTO> produtoEstoque = produtoEstoqueService.buscarPorOficina(idOficina);
        if(produtoEstoque.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.ok(produtoEstoque);
    }

    @Operation(summary = "Busca os produtos no estoque que estão com a quantidade inferior ou igual a 10 por id oficina")
    @GetMapping("/estoque-baixo/{idOficina}")
    public ResponseEntity<List<ProdutoEstoque>> buscarEstoqueBaixo(@PathVariable int idOficina){
        List<ProdutoEstoque> produtoEstoque = produtoEstoqueService.buscarEstoqueBaixo(idOficina);
        if(produtoEstoque.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.ok(produtoEstoque);
    }


}

package org.motion.motion_api.application.services;

import org.motion.motion_api.application.exceptions.RecursoNaoEncontradoException;
import org.motion.motion_api.application.services.util.ServiceHelper;
import org.motion.motion_api.domain.dtos.pitstop.produtoEstoque.CreateProdutoEstoqueDTO;
import org.motion.motion_api.domain.dtos.pitstop.produtoEstoque.ProdutoEstoqueResponseDTO;
import org.motion.motion_api.domain.dtos.pitstop.produtoEstoque.UpdateProdutoEstoqueDTO;
import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.ProdutoEstoque;
import org.motion.motion_api.domain.repositories.IOficinaRepository;
import org.motion.motion_api.domain.repositories.pitstop.IOrdemDeServicoRepository;
import org.motion.motion_api.domain.repositories.pitstop.IProdutoEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoEstoqueService {

    @Autowired
    IProdutoEstoqueRepository produtoEstoqueRepository;
    @Autowired
    IOficinaRepository oficinaRepository;
    @Autowired
    IOrdemDeServicoRepository ordemDeServicoRepository;
    @Autowired
    ServiceHelper serviceHelper;
    
    public List<ProdutoEstoqueResponseDTO> listarProdutosEstoque(){
        List<ProdutoEstoque> produtos = produtoEstoqueRepository.findAll();
        return produtos.stream().map(produto -> new ProdutoEstoqueResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getModeloVeiculo(),
                produto.getQuantidade(),
                produto.getValorVenda(),
                produto.getLocalizacao(),
                produto.getGarantia()
        )).collect(Collectors.toList());
    }
    
    public ProdutoEstoque cadastrar(CreateProdutoEstoqueDTO createProdutoEstoqueDTO){
        ProdutoEstoque produtoEstoque = new ProdutoEstoque(createProdutoEstoqueDTO, oficinaRepository.findById(createProdutoEstoqueDTO.fkOficina()).orElseThrow(()-> new RecursoNaoEncontradoException("Oficina não encontrada com o id: " + createProdutoEstoqueDTO.fkOficina())));
        Oficina oficina = serviceHelper.pegarOficinaValida(createProdutoEstoqueDTO.fkOficina());
        produtoEstoque.setOficina(oficina);
        produtoEstoqueRepository.save(produtoEstoque);
        return produtoEstoque;
    }
    
    public ProdutoEstoqueResponseDTO buscarPorId(Integer id){
        ProdutoEstoque produtoEstoque = produtoEstoqueRepository.findById(id).orElseThrow(()-> new RecursoNaoEncontradoException("Produto de estoque não encontrado com o id: " + id));
        return new ProdutoEstoqueResponseDTO(
                produtoEstoque.getId(),
                produtoEstoque.getNome(),
                produtoEstoque.getModeloVeiculo(),
                produtoEstoque.getQuantidade(),
                produtoEstoque.getValorVenda(),
                produtoEstoque.getLocalizacao(),
                produtoEstoque.getGarantia()
        );
    }

    public List<ProdutoEstoque> listarPorPreco(double precoMinimo, double precoMaximo, String nome){
        return produtoEstoqueRepository.findByValorVendaBetweenAndNomeContainingIgnoreCase(precoMinimo, precoMaximo,nome);
    }
    
    public void deletar(Integer id){
        ProdutoEstoque produtoEstoque = produtoEstoqueRepository.findById(id).orElseThrow(()-> new RecursoNaoEncontradoException("Produto de estoque não encontrado com o id: " + id));
        produtoEstoqueRepository.delete(produtoEstoque);
    }
    
    public ProdutoEstoque atualizar(Integer id, UpdateProdutoEstoqueDTO produtoEstoque){
        ProdutoEstoque produtoEstoqueAtualizado = produtoEstoqueRepository.findById(id).orElseThrow(()-> new RecursoNaoEncontradoException("Produto de estoque não encontrado com o id: " + id));
        produtoEstoqueAtualizado.setNome(produtoEstoque.nome());
        produtoEstoqueAtualizado.setModeloVeiculo(produtoEstoque.modeloVeiculo());
        produtoEstoqueAtualizado.setQuantidade(produtoEstoque.quantidade());
        produtoEstoqueAtualizado.setLocalizacao(produtoEstoque.localizacao());
        produtoEstoqueAtualizado.setValorCompra(produtoEstoque.valorCompra());
        produtoEstoqueAtualizado.setValorVenda(produtoEstoque.valorVenda());
        produtoEstoqueAtualizado.setValorComMaoObra(produtoEstoque.valorComMaoObra());
        produtoEstoqueAtualizado.setGarantia(produtoEstoque.garantia());
        produtoEstoqueRepository.save(produtoEstoqueAtualizado);
        return produtoEstoqueAtualizado;
    }
    
    public ProdutoEstoqueResponseDTO buscarPorNome(String nome){
        ProdutoEstoque produtoEstoque = produtoEstoqueRepository.findByNome(nome);
        return new ProdutoEstoqueResponseDTO(
                produtoEstoque.getId(),
                produtoEstoque.getNome(),
                produtoEstoque.getModeloVeiculo(),
                produtoEstoque.getQuantidade(),
                produtoEstoque.getValorVenda(),
                produtoEstoque.getLocalizacao(),
                produtoEstoque.getGarantia()
        );
    }

    public List<ProdutoEstoqueResponseDTO> buscarPorOficina(Integer idOficina){
        Oficina oficina = serviceHelper.pegarOficinaValida(idOficina);
        List<ProdutoEstoque> produtos = produtoEstoqueRepository.findByOficina(oficina);
        return produtos.stream().map(produto -> new ProdutoEstoqueResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getModeloVeiculo(),
                produto.getQuantidade(),
                produto.getValorVenda(),
                produto.getLocalizacao(),
                produto.getGarantia()
        )).collect(Collectors.toList());
    }

    public List<ProdutoEstoque> buscarEstoqueBaixo(Integer idOficina){
        Oficina oficina = serviceHelper.pegarOficinaValida(idOficina);
        return produtoEstoqueRepository.findByOficinaAndQuantidadeLessThanEqual(oficina, 10);
    }

}

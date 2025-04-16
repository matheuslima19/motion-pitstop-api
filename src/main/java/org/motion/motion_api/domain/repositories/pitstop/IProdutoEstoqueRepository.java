package org.motion.motion_api.domain.repositories.pitstop;

import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.ProdutoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IProdutoEstoqueRepository extends JpaRepository<ProdutoEstoque,Integer> {
    ProdutoEstoque findByNome (String nome);
    Optional<ProdutoEstoque> findByNomeAndOficina (String nome, Oficina oficina);
    List<ProdutoEstoque> findByNomeIn(List<String> nomes);
    List<ProdutoEstoque> findByOficina(Oficina oficina);
    List<ProdutoEstoque> findByValorVendaBetweenAndNomeContainingIgnoreCase(double precoMinimo, double precoMaximo, String nome);
    List<ProdutoEstoque> findByOficinaAndQuantidadeLessThanEqual(Oficina oficina, Integer quantidade);
}
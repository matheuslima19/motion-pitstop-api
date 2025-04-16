package org.motion.motion_api.domain.repositories.pitstop;

import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.ProdutoEstoque;
import org.motion.motion_api.domain.entities.pitstop.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IServicoRepository extends JpaRepository<Servico,Integer> {
    Optional<Servico> findByNomeAndOficina(String nome, Oficina oficina);
    Servico findByNome(String nome);
    List<Servico> findByOficina(Oficina oficina);
}

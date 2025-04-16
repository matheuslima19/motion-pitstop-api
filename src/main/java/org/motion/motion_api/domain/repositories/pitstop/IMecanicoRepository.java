package org.motion.motion_api.domain.repositories.pitstop;

import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.Mecanico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IMecanicoRepository extends JpaRepository<Mecanico,Integer> {
    List<Mecanico> findByNome(String nome);
    List<Mecanico> findAllByOficina(Oficina oficina);
}

package org.motion.motion_api.domain.repositories.pitstop;

import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.Cliente;
import org.motion.motion_api.domain.entities.pitstop.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVeiculoRepository extends JpaRepository<Veiculo,Integer> {
    List<Veiculo> findAllByCliente(Cliente cliente);
    List<Veiculo> findAllByCliente_Oficina(Oficina oficina);
}

package org.motion.motion_api.domain.repositories.buscar;

import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.buscar.ServicoBuscar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IServicoBuscarRepository extends JpaRepository<ServicoBuscar, Integer> {
    List<ServicoBuscar> findByOficina(Oficina oficina);
    List<ServicoBuscar> findAllByOficina_InformacoesOficinaTipoVeiculosTrabalhaContainingIgnoreCaseAndNomeContainingIgnoreCase(String tipoVeiculo,String nome);
}

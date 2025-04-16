package org.motion.motion_api.domain.repositories;

import org.motion.motion_api.domain.entities.Oficina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOficinaRepository extends JpaRepository<Oficina,Integer> {
    List<Oficina> findByInformacoesOficina_TipoVeiculosTrabalhaContainingIgnoreCase(String tipoVeiculosTrabalha);
    List<Oficina> findByInformacoesOficina_TipoVeiculosTrabalhaContainingIgnoreCaseAndInformacoesOficina_TipoPropulsaoTrabalhaContainingIgnoreCaseAndInformacoesOficina_MarcasVeiculosTrabalhaContainingIgnoreCaseAndNomeContainingIgnoreCase(String tipoVeiculosTrabalha, String tipoPropulsaoTrabalha, String marcasVeiculosTrabalha, String nome);
}

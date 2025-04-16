package org.motion.motion_api.domain.repositories.pitstop;

import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.OrdemDeServico;
import org.motion.motion_api.domain.entities.pitstop.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IOrdemDeServicoRepository extends JpaRepository<OrdemDeServico,Integer> {
    boolean existsByVeiculo(Veiculo veiculo);
    OrdemDeServico findByToken (String token);
    List<OrdemDeServico> findAllByOficina(Oficina oficina);
    Integer countByDataInicioAndStatusEqualsIgnoreCaseAndOficina(LocalDate dataInicio, String status, Oficina oficina);
    Integer countByDataInicioBetweenAndOficina(LocalDate dataInicio, LocalDate dataFim, Oficina oficina);
    List<OrdemDeServico> findAllByVeiculo_Cliente_Email(String email);
}

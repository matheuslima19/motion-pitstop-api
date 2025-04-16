package org.motion.motion_api.domain.repositories.pitstop;

import org.motion.motion_api.domain.entities.pitstop.Financeiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IFinanceiroRepository extends JpaRepository<Financeiro,Integer> {
    List<Financeiro> findAllByOficina_Id(int id);
    List<Financeiro> findAllByOficina_IdAndDataBetween(int id, LocalDate primeiroDiaMesAtual, LocalDate ultimoDiaMesAtual);
}

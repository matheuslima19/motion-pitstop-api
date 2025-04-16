package org.motion.motion_api.domain.repositories.pitstop;


import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.FotoGaleriaOficina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoGaleriaOficinaRepository extends JpaRepository<FotoGaleriaOficina, Integer> {

    List<FotoGaleriaOficina> findAllByOficinaIs(Oficina oficina);
}

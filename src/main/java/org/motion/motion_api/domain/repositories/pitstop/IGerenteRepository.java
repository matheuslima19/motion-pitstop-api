package org.motion.motion_api.domain.repositories.pitstop;

import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.Gerente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface IGerenteRepository extends JpaRepository<Gerente,Integer> {
     Gerente findGerenteByEmail(String email);
     boolean existsByEmail(String email);
     UserDetails findByEmail(String email);

     boolean existsByOficina(Oficina oficina);
}

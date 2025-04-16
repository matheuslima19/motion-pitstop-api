package org.motion.motion_api.application.services.strategies;


import org.motion.motion_api.domain.dtos.pitstop.gerente.*;
import org.motion.motion_api.domain.entities.pitstop.Gerente;

public interface GerenteServiceStrategy extends BaseService<Gerente, UpdateGerenteDTO, CreateGerenteDTO>  {
    Gerente atualizarSenha(int id, UpdateSenhaGerenteDTO updateSenhaGerenteDTO);
    LoginGerenteResponse login(LoginGerenteRequest request);

}

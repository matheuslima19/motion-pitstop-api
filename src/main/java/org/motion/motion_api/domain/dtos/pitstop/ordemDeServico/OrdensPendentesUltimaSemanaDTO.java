package org.motion.motion_api.domain.dtos.pitstop.ordemDeServico;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class OrdensPendentesUltimaSemanaDTO {
    Integer qtd;
    String diaSemana;
}

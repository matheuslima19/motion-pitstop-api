package org.motion.motion_api.domain.dtos.pitstop.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateServicoDTO(
        @NotBlank
        String nome,
        @NotBlank
        String descricao,
        @NotNull
        Double valorServico,
        @NotBlank
        String garantia,
        @NotNull
        Integer fkOficina) {
}

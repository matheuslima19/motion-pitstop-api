package org.motion.motion_api.domain.dtos.buscar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateServicoBuscarDTO(
        @NotBlank
        String nome,
        @NotBlank
        String descricao,
        @NotNull
        Integer fkOficina
) {
}

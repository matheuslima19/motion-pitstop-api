package org.motion.motion_api.domain.dtos.pitstop.mecanico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMecanicoDTO(
        @NotNull
        Integer fkOficina,
        @NotBlank
        String nome,
        @NotBlank
        String telefone
) {
}

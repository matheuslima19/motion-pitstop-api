package org.motion.motion_api.domain.dtos.pitstop.gerente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateGerenteDTO(
         @NotBlank
        String email,
         @NotBlank
        String nome,
        @NotBlank
        String sobrenome,
        @NotNull
        Integer fkOficina
) {
}

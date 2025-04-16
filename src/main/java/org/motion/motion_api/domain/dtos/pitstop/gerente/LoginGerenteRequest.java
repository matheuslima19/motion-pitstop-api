package org.motion.motion_api.domain.dtos.pitstop.gerente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginGerenteRequest(
        @NotNull @NotBlank
        String email,
        @NotNull @NotBlank
        String senha
) { }

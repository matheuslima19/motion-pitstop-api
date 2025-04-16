package org.motion.motion_api.domain.dtos.pitstop.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateClienteDTO(
        @NotBlank
        String nome,
        @NotBlank
        String telefone,
        @NotBlank
        String email,
        @NotNull
        Integer fkOficina) {
}

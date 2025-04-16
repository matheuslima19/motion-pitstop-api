package org.motion.motion_api.domain.dtos.pitstop.veiculo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateVeiculoDTO(
        @NotBlank
        String placa,
        @NotBlank
        String marca,
        @NotBlank
        String modelo,
        @NotBlank
        String cor,
        @NotNull
        Integer ano,
        @NotNull
        Integer fkCliente) {
}

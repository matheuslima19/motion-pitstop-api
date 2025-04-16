package org.motion.motion_api.domain.dtos.pitstop.mecanico;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateMecanicoDTO {
    @NotBlank
    private String nome;
    @NotBlank
    private String telefone;
}

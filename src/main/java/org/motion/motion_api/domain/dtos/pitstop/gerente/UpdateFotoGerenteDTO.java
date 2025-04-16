package org.motion.motion_api.domain.dtos.pitstop.gerente;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateFotoGerenteDTO {
    @NotBlank
    private String url;
}

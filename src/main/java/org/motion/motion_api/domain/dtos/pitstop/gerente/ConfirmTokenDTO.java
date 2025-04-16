package org.motion.motion_api.domain.dtos.pitstop.gerente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConfirmTokenDTO {
    @NotBlank
    private String email;
    private String senha;
    @NotBlank
    private String token;
}

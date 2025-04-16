package org.motion.motion_api.domain.dtos.pitstop.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateServicoDTO {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotNull
    private Double valorServico;
    @NotBlank
    private String garantia;
}

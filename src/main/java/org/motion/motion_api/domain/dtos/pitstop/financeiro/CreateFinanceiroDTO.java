package org.motion.motion_api.domain.dtos.pitstop.financeiro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateFinanceiroDTO {
    @NotBlank
    private String transacao;
    @NotBlank
    private String categoria;
    @NotNull
    private LocalDate data;
    @NotNull
    private Double valor;
    @NotNull
    private String formaPagamento;
    @NotNull
    private Integer idOficina;
}

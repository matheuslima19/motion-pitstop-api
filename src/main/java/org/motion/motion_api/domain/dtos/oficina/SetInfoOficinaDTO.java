package org.motion.motion_api.domain.dtos.oficina;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SetInfoOficinaDTO {
    private String whatsapp;
    private String horarioIniSem;
    private String horarioFimSem;
    private String horarioIniFds;
    private String horarioFimFds;
    private String diasSemanaAberto;
    private String tipoVeiculosTrabalha;
    private String tipoPropulsaoTrabalha;
    private String marcasVeiculosTrabalha;
}

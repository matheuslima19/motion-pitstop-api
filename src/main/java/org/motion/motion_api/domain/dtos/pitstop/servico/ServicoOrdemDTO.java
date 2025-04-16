package org.motion.motion_api.domain.dtos.pitstop.servico;

public record ServicoOrdemDTO(
        String nome,
        String garantia,
        Double valor
) {
}

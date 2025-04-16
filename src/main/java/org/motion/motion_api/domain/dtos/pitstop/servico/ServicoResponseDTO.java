package org.motion.motion_api.domain.dtos.pitstop.servico;

public record ServicoResponseDTO(
        Integer id,
        String nome,
        String descricao,
        Double valorServico,
        String garantia
) {}

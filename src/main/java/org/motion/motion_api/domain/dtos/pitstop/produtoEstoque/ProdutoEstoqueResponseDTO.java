package org.motion.motion_api.domain.dtos.pitstop.produtoEstoque;

public record ProdutoEstoqueResponseDTO(
        Integer id,
        String nome,
        String modeloVeiculo,
        Integer quantidade,
        Double valorVenda,
        String localizacao,
        String garantia
) {}
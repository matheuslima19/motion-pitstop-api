package org.motion.motion_api.domain.dtos.pitstop.produtoEstoque;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateProdutoEstoqueDTO(
        @NotBlank
        String nome,
        @NotBlank
        String modeloVeiculo,
        @NotNull
        Integer quantidade,
        @NotBlank
        String localizacao,
        @NotNull
        Double valorCompra,
        @NotNull
        Double valorVenda,
        @NotNull
        Double valorComMaoObra,
        @NotBlank
        String garantia
) {
}

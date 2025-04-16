package org.motion.motion_api.domain.dtos.pitstop.produtoEstoque;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.motion.motion_api.domain.entities.pitstop.ProdutoEstoque;

public record CreateProdutoEstoqueDTO(
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
        String garantia,
        @NotNull
        Integer fkOficina
) {
}

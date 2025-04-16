package org.motion.motion_api.domain.dtos.pitstop.ordemDeServico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.motion.motion_api.domain.dtos.pitstop.produtoEstoque.ProdutoOrdemDTO;
import org.motion.motion_api.domain.dtos.pitstop.servico.ServicoOrdemDTO;

import java.time.LocalDate;
import java.util.List;


public record CreateOrdemDeServicoDTO(
        @NotNull
        Integer fkOficina,
        @NotBlank
        String status,
        @NotBlank
        String garantia,
        @NotNull
        @NotNull
        Integer fkVeiculo,
        Integer fkMecanico,
        @NotNull
        LocalDate dataInicio,
        LocalDate dataFim,
        String tipoOs,
        @NotNull
        Integer fkCliente,
        List<ProdutoOrdemDTO> produtos,
        List<ServicoOrdemDTO> servicos,
        String observacoes,
        Double valorTotal) {
}

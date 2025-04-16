package org.motion.motion_api.domain.dtos.pitstop.ordemDeServico;

import org.motion.motion_api.domain.dtos.pitstop.produtoEstoque.ProdutoOrdemDTO;
import org.motion.motion_api.domain.dtos.pitstop.servico.ServicoOrdemDTO;
import org.motion.motion_api.domain.entities.Oficina;
import java.time.LocalDate;
import java.util.List;

public record OrdemCriadaDTO(
         Integer id,
         Oficina oficina,
         String status,
         LocalDate dataInicio,
         LocalDate dataFim,
         String token,
         String tipoOs,
         String garantia,

            String nomeCliente,
            String telefoneCliente,
            String emailCliente,

            String placaVeiculo,
            String marcaVeiculo,
            String modeloVeiculo,
            Integer anoVeiculo,
            String corVeiculo,


            String nomeMecanico,
            String telefoneMecanico,


         Double valorTotal,
         String observacoes,


         List<ProdutoOrdemDTO> produtos,
         List<ServicoOrdemDTO> servicos
) {
}

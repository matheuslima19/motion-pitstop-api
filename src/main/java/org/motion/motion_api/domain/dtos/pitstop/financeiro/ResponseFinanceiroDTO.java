package org.motion.motion_api.domain.dtos.pitstop.financeiro;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.motion.motion_api.domain.entities.pitstop.Financeiro;

import java.time.LocalDate;

@Data @NoArgsConstructor
public class ResponseFinanceiroDTO {
    private Integer idMovimento;
    private String transacao;
    private String categoria;
    private LocalDate data;
    private Double valor;
    private String formaPagamento;

    public ResponseFinanceiroDTO(Financeiro financeiro) {
        this.idMovimento = financeiro.getId();
        this.transacao = financeiro.getTransacao();
        this.categoria = financeiro.getCategoria();
        this.data = financeiro.getData();
        this.valor = financeiro.getValor();
        this.formaPagamento = financeiro.getFormaPagamento();
    }
}




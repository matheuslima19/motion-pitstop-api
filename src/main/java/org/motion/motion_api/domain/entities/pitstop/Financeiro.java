package org.motion.motion_api.domain.entities.pitstop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.motion.motion_api.domain.dtos.pitstop.financeiro.CreateFinanceiroDTO;
import org.motion.motion_api.domain.entities.Oficina;

import java.time.LocalDate;


@Table(name = "Pitstop_Financeiro")
@Entity(name = "Financeiro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Financeiro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String transacao;
    private String categoria;
    private LocalDate data;
    private Double valor;
    private String formaPagamento;

    @ManyToOne @JoinColumn(name = "fkOficina") @NotNull @JsonIgnore
    private Oficina oficina;

    public Financeiro(CreateFinanceiroDTO dto, Oficina oficina) {
        this.transacao = dto.getTransacao();
        this.categoria = dto.getCategoria();
        this.data = dto.getData();
        this.valor = dto.getValor();
        this.formaPagamento = dto.getFormaPagamento();
        this.oficina = oficina;
    }
}

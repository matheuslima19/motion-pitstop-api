package org.motion.motion_api.domain.entities.pitstop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.motion.motion_api.domain.entities.Oficina;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Pitstop_OrdemDeServico")
@Entity(name = "OrdemDeServico")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrdemDeServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String status;
    private Double desconto;
    private Double valorTotal;
    private String token;
    private String tipoOs;
    private String garantia;
    private String observacoes;


    @ManyToOne
    @JoinColumn(name = "fkOficina")
    @JsonIgnore
    private Oficina oficina;


    @ManyToOne
    @JoinColumn(name = "fkVeiculo")
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "fkCliente")
    private Cliente cliente;


    @ManyToOne
    @JoinColumn(name = "fkMecanico")
    private Mecanico mecanico;


    @OneToMany(
            mappedBy = "ordemDeServico",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<ProdutoOrdemServico> produtos = new ArrayList<>();


    @OneToMany(
            mappedBy = "ordemDeServico",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<ServicoOrdemServico> servicos = new ArrayList<>();

}

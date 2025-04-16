package org.motion.motion_api.domain.entities.pitstop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.motion.motion_api.domain.dtos.pitstop.servico.CreateServicoDTO;
import org.motion.motion_api.domain.entities.Oficina;

@Table(name = "Pitstop_Servico")
@Entity(name = "Servico")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Servico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private Double valorServico;
    private String garantia;

    @ManyToOne @JoinColumn(name = "fkOficina") @NotNull
    @JsonIgnore
    private Oficina oficina;

    @ManyToOne
    @JoinColumn(name = "ordem_de_servico_id")
    private OrdemDeServico ordemDeServico;

    public Servico(CreateServicoDTO createServicoDTO, Oficina oficina) {
        this.nome = createServicoDTO.nome();
        this.descricao = createServicoDTO.descricao();
        this.valorServico = createServicoDTO.valorServico();
        this.garantia = createServicoDTO.garantia();
        this.oficina = oficina;
    }
}


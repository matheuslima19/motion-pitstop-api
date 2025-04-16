package org.motion.motion_api.domain.entities.pitstop;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Pitstop_ServicoOrdemServico")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServicoOrdemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private Double valor;
    @Column(nullable = false)
    private String garantia;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "ordem_de_servico_id")
    private OrdemDeServico ordemDeServico;

    public ServicoOrdemServico(String nome, Double valor, String garantia) {
        this.nome = nome;
        this.valor = valor;
        this.garantia = garantia;
    }
}

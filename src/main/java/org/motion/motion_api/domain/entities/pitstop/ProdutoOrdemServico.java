package org.motion.motion_api.domain.entities.pitstop;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Pitstop_ProdutoOrdemServico")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProdutoOrdemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private Double valor;
    @Column(nullable = false)
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "produto_estoque_id")
    private ProdutoEstoque produtoEstoque;

    @ManyToOne
    @JoinColumn(name = "ordem_de_servico_id")
    private OrdemDeServico ordemDeServico;

    public ProdutoOrdemServico(String nome, Double valor, Integer quantidade) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
    }
}

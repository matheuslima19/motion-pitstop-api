package org.motion.motion_api.domain.entities.pitstop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.motion.motion_api.domain.dtos.pitstop.produtoEstoque.CreateProdutoEstoqueDTO;
import org.motion.motion_api.domain.entities.Oficina;

@Table(name = "Pitstop_ProdutoEstoque")
@Entity(name = "ProdutoEstoque")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProdutoEstoque {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String modeloVeiculo;
    private Integer quantidade;
    private Double valorComMaoObra;
    private Double valorCompra;
    private Double valorVenda;
    private String localizacao;
    private String garantia;

    @ManyToOne @JoinColumn(name = "fkOficina") @NotNull
    private Oficina oficina;

    @ManyToOne
    @JoinColumn(name = "ordem_de_servico_id")
    private OrdemDeServico ordemDeServico;

    public ProdutoEstoque(CreateProdutoEstoqueDTO createProdutoEstoqueDTO, Oficina oficina) {
        this.nome = createProdutoEstoqueDTO.nome();
        this.modeloVeiculo = createProdutoEstoqueDTO.modeloVeiculo();
        this.localizacao = createProdutoEstoqueDTO.localizacao();
        this.quantidade = createProdutoEstoqueDTO.quantidade();
        this.valorCompra = createProdutoEstoqueDTO.valorCompra();
        this.valorVenda = createProdutoEstoqueDTO.valorVenda();
        this.valorComMaoObra = createProdutoEstoqueDTO.valorComMaoObra();
        this.garantia = createProdutoEstoqueDTO.garantia();
        this.oficina = oficina;
    }
}

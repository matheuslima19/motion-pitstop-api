package org.motion.motion_api.domain.entities.buscar;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.motion.motion_api.domain.dtos.buscar.CreateServicoBuscarDTO;
import org.motion.motion_api.domain.entities.Oficina;

@Table(name = "Buscar_Servico")
@Entity(name = "ServicoBuscar")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServicoBuscar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "fkOficina")
    private Oficina oficina;

    public ServicoBuscar(CreateServicoBuscarDTO novoServicoBuscar) {
        this.nome = novoServicoBuscar.nome();
        this.descricao = novoServicoBuscar.descricao();
    }
}

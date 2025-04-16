package org.motion.motion_api.domain.entities.pitstop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.motion.motion_api.domain.dtos.pitstop.cliente.CreateClienteDTO;
import org.motion.motion_api.domain.entities.Oficina;

import java.time.LocalDate;

@Table(name = "Pitstop_Cliente")
@Entity(name = "Cliente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cliente {
    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String telefone;
    private String email;
    @JsonIgnore
    private LocalDate deletedAt;

    @ManyToOne @JoinColumn(name = "fkOficina")
    @JsonIgnore
    private Oficina oficina;

    public Cliente(CreateClienteDTO createClienteDTO, Oficina oficina) {
        this.nome = createClienteDTO.nome();
        this.telefone = createClienteDTO.telefone();
        this.email = createClienteDTO.email();
        this.oficina = oficina;
    }
}

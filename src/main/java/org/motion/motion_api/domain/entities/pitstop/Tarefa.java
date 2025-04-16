package org.motion.motion_api.domain.entities.pitstop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.motion.motion_api.domain.dtos.pitstop.tarefa.CreateTarefaDTO;
import org.motion.motion_api.domain.entities.Oficina;

import java.time.LocalDate;


@Table(name = "Pitstop_Tarefa")
@Entity(name = "Tarefa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tarefa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    private LocalDate dtDeadline;
    private String status;


    public Tarefa(CreateTarefaDTO createTarefaDTO, Oficina oficina) {
        this.descricao = createTarefaDTO.descricao();
        this.dtDeadline = createTarefaDTO.dtDeadline();
        this.status = createTarefaDTO.status();
        this.oficina = oficina;
    }

    @ManyToOne @JoinColumn(name = "fkOficina") @NotNull @JsonIgnore
    private Oficina oficina;
}

package org.motion.motion_api.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.motion.motion_api.domain.entities.pitstop.Gerente;
import org.motion.motion_api.domain.entities.pitstop.InformacoesOficina;

import java.util.Set;

@Table(name = "Pitstop_Oficina")
@Entity(name = "Oficina")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Oficina {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;
    @NotNull @NotBlank
    private String nome;
    @NotNull @NotBlank
    private String cnpj;
    @NotNull @NotBlank
    private String cep;
    @NotNull @NotBlank
    private String numero;
    private String complemento;
    private boolean hasBuscar;
    private String logoUrl;

    @OneToMany(mappedBy = "oficina", cascade = CascadeType.ALL) @JsonIgnore
    private Set<Gerente> gerentes;

    @OneToOne @JsonProperty(access = JsonProperty.Access.READ_ONLY) @Schema(hidden = true)
    private InformacoesOficina informacoesOficina;


}

package org.motion.motion_api.domain.entities.pitstop;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.motion.motion_api.domain.entities.Oficina;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InformacoesOficina {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String whatsapp;
    private String horarioIniSem;
    private String horarioFimSem;
    private String horarioIniFds;
    private String horarioFimFds;
    private String diasSemanaAberto;
    private String tipoVeiculosTrabalha;
    private String tipoPropulsaoTrabalha;
    @Column(columnDefinition = "TEXT")
    private String marcasVeiculosTrabalha;

    public InformacoesOficina(String whatsapp, String horarioIniSem, String horarioFimSem, String horarioIniFds, String horarioFimFds, String diasSemanaAberto, String tipoVeiculosTrabalha, String tipoPropulsaoTrabalha, String marcasVeiculosTrabalha) {
        this.whatsapp = whatsapp;
        this.horarioIniSem = horarioIniSem;
        this.horarioFimSem = horarioFimSem;
        this.horarioIniFds = horarioIniFds;
        this.horarioFimFds = horarioFimFds;
        this.diasSemanaAberto = diasSemanaAberto;
        this.tipoVeiculosTrabalha = tipoVeiculosTrabalha;
        this.tipoPropulsaoTrabalha = tipoPropulsaoTrabalha;
        this.marcasVeiculosTrabalha = marcasVeiculosTrabalha;
    }
}

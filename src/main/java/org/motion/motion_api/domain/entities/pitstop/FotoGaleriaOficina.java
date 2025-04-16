package org.motion.motion_api.domain.entities.pitstop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.motion.motion_api.domain.entities.Oficina;

@Entity
@Getter @Setter
public class FotoGaleriaOficina {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String url;

    @ManyToOne @JsonIgnore
    private Oficina oficina;
}

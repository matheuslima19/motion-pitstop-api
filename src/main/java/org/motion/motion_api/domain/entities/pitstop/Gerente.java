package org.motion.motion_api.domain.entities.pitstop;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.motion.motion_api.domain.dtos.pitstop.gerente.CreateGerenteDTO;
import org.motion.motion_api.domain.entities.Oficina;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "Gerente")
@Table(name = "Pitstop_Gerente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Gerente implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull @NotBlank
    private String nome;
    @NotNull @NotBlank
    private String sobrenome;
    @NotNull @NotBlank
    private String email;
    private String senha;
    private String fotoUrl;
    private String confirmToken;

    @ManyToOne @JoinColumn(name = "fk_oficina") @NotNull
    private Oficina oficina;


    public Gerente(CreateGerenteDTO createGerenteDTO, Oficina oficina,String senhaAutomatica) {
        this.nome = createGerenteDTO.nome();
        this.sobrenome = createGerenteDTO.sobrenome();
        this.email = createGerenteDTO.email();
        this.senha = senhaAutomatica;
        this.oficina = oficina;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true ;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

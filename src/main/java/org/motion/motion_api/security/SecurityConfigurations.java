package org.motion.motion_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/docs").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/gerentes/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/oficinas").permitAll()
                        .requestMatchers(HttpMethod.GET,"/oficinas").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/oficinas").permitAll()
                        .requestMatchers(HttpMethod.GET,"/oficinas/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/buscar-servicos/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/ordemDeServicos/token/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/galerias/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/produtoEstoque/**").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/infos-oficina/atualiza-zap/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/gerentes/set-token").permitAll()
                        .requestMatchers(HttpMethod.POST,"/gerentes/confirmar-token").permitAll()
                        .requestMatchers(HttpMethod.GET,"/ordemDeServicos/cliente/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

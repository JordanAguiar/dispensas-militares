package com.aguiar.dispensas_militares.config;

import com.aguiar.dispensas_militares.service.UsuarioDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsuarioDetailsService usuarioDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/esqueci-senha", "/redefinir-senha", "/acesso-negado", "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()

                        .requestMatchers("/dashboard").hasAnyAuthority(
                                "ROLE_MODERADOR", "ROLE_ADMINISTRADOR", "ROLE_CONSULTOR", "ROLE_AFILHADO"
                        )
                        // Apenas MODERADOR,ADMINISTRADOR pode gerenciar usuários
                        .requestMatchers("/usuarios/**").hasAnyAuthority("ROLE_MODERADOR", "ROLE_ADMINISTRADOR")

                        // CONSULTOR só acessa GET (visualizar)
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/militares", "/militares/", "/dispensas/**").hasAnyAuthority(
                                "ROLE_MODERADOR", "ROLE_ADMINISTRADOR", "ROLE_CONSULTOR", "ROLE_AFILHADO"
                        )

                        // AFILHADO pode criar e alterar mas não deletar
                        .requestMatchers("/militares/novo", "/militares/salvar", "/dispensas/salvar/**").hasAnyAuthority(
                                "ROLE_MODERADOR", "ROLE_ADMINISTRADOR", "ROLE_AFILHADO"
                        )

                        // Apenas MODERADOR e ADMINISTRADOR podem deletar
                        .requestMatchers("/militares/deletar/**", "/dispensas/deletar/**").hasAnyAuthority(
                                "ROLE_MODERADOR", "ROLE_ADMINISTRADOR"
                        )

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/acesso-negado")
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/militares", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        builder
                .userDetailsService(usuarioDetailsService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }
}
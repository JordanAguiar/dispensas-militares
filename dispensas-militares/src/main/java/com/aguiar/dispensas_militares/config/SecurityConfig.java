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

    private final CustomAuthenticationSuccessHandler successHandler;
    private final UsuarioDetailsService usuarioDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/esqueci-senha", "/redefinir-senha", "/acesso-negado", "/2fa/**", "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                        .requestMatchers("/dashboard").hasAnyAuthority("ROLE_MODERADOR", "ROLE_ADMINISTRADOR", "ROLE_CONSULTOR", "ROLE_AFILHADO")
                        .requestMatchers("/usuarios/**").hasAnyAuthority("ROLE_MODERADOR", "ROLE_ADMINISTRADOR")
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/militares", "/militares/", "/dispensas/**").hasAnyAuthority("ROLE_MODERADOR", "ROLE_ADMINISTRADOR", "ROLE_CONSULTOR", "ROLE_AFILHADO")
                        .requestMatchers("/militares/novo", "/militares/salvar", "/dispensas/salvar/**").hasAnyAuthority("ROLE_MODERADOR", "ROLE_ADMINISTRADOR", "ROLE_AFILHADO")
                        .requestMatchers("/militares/deletar/**", "/dispensas/deletar/**").hasAnyAuthority("ROLE_MODERADOR", "ROLE_ADMINISTRADOR")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/acesso-negado")
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(successHandler)
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
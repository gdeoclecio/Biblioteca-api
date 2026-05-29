package br.com.biblioteca.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Classe que define todas as regras de segurança: quais rotas são publicas, quais precisam de autenticação.
// O spring bloqueia todas as rotas por padrão após adicionar a dependencia security, essa classe sobrescreve esse comportamento e define as permissões 
@Configuration
public class SecurityConfig {

        @Autowired
        private JwtFilter jwtFilter;


    @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Autenticação pública
                .requestMatchers("/auth/**").permitAll()
                // Swagger público
                .requestMatchers(
                    "/",
                    "/index.html",
                    "/style.css",

                    "/v3/api-docs/**",

                    "/swagger-ui/**",
                    "/swagger-ui.html",

                    "/swagger-ui/index.html"
                ).permitAll()
                // Tudo mais exige token
                .anyRequest().authenticated()
            )
            .httpBasic(httpBasic -> httpBasic.disable())
            .formLogin(form -> form.disable())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
 
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
  }
}



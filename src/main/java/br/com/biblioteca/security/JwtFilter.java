package br.com.biblioteca.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.biblioteca.exceptions.ErroEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtService jwtService;

    private static final List<String> PUBLIC_PATHS = List.of(
        "/index.html",
        "/style.css",
        "/auth",
        "/v3/api-docs",
        "/swagger-ui",
        "/swagger-resources",
        "/webjars"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            escreverErro(response, ErroEnum.TOKEN_AUSENTE);
            return;
        }

        try {
            String token = authHeader.substring(7).trim();
            log.info("Token recebido: {}", token);

            String email = jwtService.extractEmail(token);
            log.info("Email extraído: {}", email);

            if (email == null || !jwtService.validateToken(token, email)) {
                log.info("Token inválido!");
                escreverErro(response, ErroEnum.TOKEN_INVALIDO);
                return;
            }

            log.info("Token válido, email: {}", email);

            var authentication = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    new ArrayList<>()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            log.error("JWT filter error: {}", e.getMessage());
            escreverErro(response, ErroEnum.TOKEN_INVALIDO);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void escreverErro(HttpServletResponse response, ErroEnum erro) throws IOException {
        response.setStatus(erro.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.format(
            "{\"status\": %d, \"erro\": \"%s\"}",
            erro.getStatus().value(),
            erro.getMensagem()
        ));
    }
}
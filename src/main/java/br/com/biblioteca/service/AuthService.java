package br.com.biblioteca.service;

import org.springframework.stereotype.Service;

import br.com.biblioteca.dto.LoginRequestDTO;
import br.com.biblioteca.entity.Usuario;
import br.com.biblioteca.repository.UsuarioRepository;
import br.com.biblioteca.security.JwtService;

@Service
public class AuthService {
     private final UsuarioRepository repository;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository repository,
                       JwtService jwtService) {
        this.repository = repository;
        this.jwtService = jwtService;
    }

    public String login(LoginRequestDTO dto) {

        Usuario user = repository.findByEmail(dto.email())
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        if (!user.getSenha().equals(dto.senha())) {
            throw new RuntimeException("Senha inválida");
        }

        return jwtService.generateToken(user.getEmail());
    }
}

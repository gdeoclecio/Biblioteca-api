package br.com.biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.biblioteca.dto.LoginRequestDTO;
import br.com.biblioteca.dto.LoginResponseDTO;
import br.com.biblioteca.entity.Usuario;
import br.com.biblioteca.exceptions.ApiException;
import br.com.biblioteca.exceptions.ErroEnum;
import br.com.biblioteca.repository.UsuarioRepository;
import br.com.biblioteca.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

//Classe controller responsável pelos endpoints de autenticação da API. Permite que o usuario faça login através da rota POST/auth/login para adquirir o token.
@Tag(name = "Autenticação", description = "Login e geração de token JWT")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;
 
    @Autowired
    private JwtService jwtService;
 
    @Autowired
    private PasswordEncoder passwordEncoder;

    //metodo que autentica o usuario e retorna o token caso email e senha forem validos.
    @Operation(summary = "Fazer login e obter token JWT")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new ApiException(ErroEnum.USUARIO_INVALIDO));
        
         // compara a senha digitada com Bcrypt salvo no banco
        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new ApiException(ErroEnum.SENHA_INVALIDA);
        }
       //Se as credencas estiverem corretas, token vai ser gerado.
        String token = jwtService.generateToken(usuario.getEmail());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }


}

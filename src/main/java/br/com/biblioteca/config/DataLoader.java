package br.com.biblioteca.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.biblioteca.entity.Usuario;
import br.com.biblioteca.repository.UsuarioRepository;


//Cria um usuario no banco toda vez que a aplicação sobe, garantindo um usuario "admin" na tabela usuario, esse usuario sera usado para login.
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.findByEmail("admin@email.com").isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setEmail("admin@email.com");

            // criptografa a senha antes de salvar, para não ser armazenada em texto puro.
            usuario.setSenha(passwordEncoder.encode("123456"));
            usuarioRepository.save(usuario);
            System.out.println("✅ Usuário admin criado: admin@email.com / 123456");
        }
    }
}

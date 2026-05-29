package br.com.biblioteca.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.biblioteca.entity.Usuario;
import br.com.biblioteca.repository.UsuarioRepository;
//Spring security chama essa classe automaticamente sempre que precisa carregar um usuario para a autenticação.
@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
   
    //Busca um usuario no banco de dados pelo email e converte para o formato que o Spring SEcurity entende
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        //Constrói o objeto UserDetails do Spring a partir dos dados do banco.
        //"O ROLE_USER" é a permissão padrão atribuida a todos os usuários.
        return org.springframework.security.core.userdetails.User
        .withUsername(usuario.getEmail())
        .password(usuario.getSenha())
        .authorities("ROLE_USER")
        .build();
    }
}

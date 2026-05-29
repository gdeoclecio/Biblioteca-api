
package br.com.biblioteca.service;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.biblioteca.dto.LivroResponseDTO;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${app.email.destinatario}")
    private String destinatario;

    public void enviarEmailCadastroLivro(LivroResponseDTO livro) {

        Context context = new Context();

        context.setVariable("titulo", livro.titulo());
        context.setVariable("isbn", livro.isbn());
        context.setVariable("ano", livro.anoPublicacao());
        context.setVariable("autor", livro.autorNome());
        context.setVariable("genero", livro.generoNome());
        context.setVariable("editora", livro.editoraNome());

        String html = templateEngine.process(
                "email/livro-criado",
                context
        );

        enviar(
                "Novo livro cadastrado: " + livro.titulo(),
                html
        );
    }

    public void enviarEmailAtualizacaoLivro(LivroResponseDTO livro) {

        Context context = new Context();

        context.setVariable("titulo", livro.titulo());
        context.setVariable("isbn", livro.isbn());
        context.setVariable("ano", livro.anoPublicacao());
        context.setVariable("autor", livro.autorNome());
        context.setVariable("genero", livro.generoNome());
        context.setVariable("editora", livro.editoraNome());

        String html = templateEngine.process(
                "email/livro-atualizado",
                context
        );

        enviar(
                "Livro atualizado: " + livro.titulo(),
                html
        );
    }

    public void enviarEmailExclusaoLivro(LivroResponseDTO livro) {

        Context context = new Context();

        context.setVariable("titulo", livro.titulo());
        context.setVariable("isbn", livro.isbn());
        context.setVariable("ano", livro.anoPublicacao());
        context.setVariable("autor", livro.autorNome());
        context.setVariable("genero", livro.generoNome());
        context.setVariable("editora", livro.editoraNome());

        String html = templateEngine.process(
                "email/livro-excluido",
                context
        );

        enviar(
                "Livro excluído: " + livro.titulo(),
                html
        );
    }

    private void enviar(String assunto, String html) {

        try {

            MimeMessage mensagem =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(mensagem, true);

            helper.setTo(destinatario);

            helper.setSubject(assunto);

            helper.setText(html, true);

            mailSender.send(mensagem);

        } catch (Exception e) {

            System.err.println(
                    "Aviso: falha ao enviar email — "
                    + e.getMessage()
            );
        }
    }
}


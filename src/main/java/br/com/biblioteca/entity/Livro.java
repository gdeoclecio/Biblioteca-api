package br.com.biblioteca.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "livro")
public class Livro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;


    @Column(name = "isbn", nullable = false, length = 20)
    private String isbn;

   
    @Column(name = "ano_publicacao")
    private Integer anoPublicacao;

   @ManyToOne(fetch = FetchType.EAGER)// esse livro pertence a um autor
   @JoinColumn(name = "id_autor", nullable = false)//coluna da chave estrangeira
    private Autor autor;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "id_genero", nullable = false)
   private Genero genero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_editora", nullable = false)
    private Editora editora;

    public Livro(){}

     // construtor recebendo RequestDTO e Autor para o Service ficar mais limpo
    public Livro(String titulo, String isbn, Integer anoPublicacao, Genero genero, Editora editora, Autor autor){
       this.titulo = titulo;
       this.isbn = isbn;
       this.anoPublicacao = anoPublicacao;
       this.genero = genero;
       this.editora = editora;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }



}

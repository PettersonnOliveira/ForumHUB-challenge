package alura.com.ForumHUB.domain.Topico;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String titulo;
    @NotBlank
    private String mensagem;
    @NotNull
    @Column(name = "data_criacao")
    private LocalDateTime DataCriacao;
    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusTopico status;
    @NotNull
    @Column(name = "autor_id")
    private Long autor;
    @NotNull
    @Column(name = "curso_id")
    private Long curso;


    public void atualizarInformacoes(DadosAtualizarTopicos dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
        // Adicione mais campos aqui conforme DadosAtualizarTopicos
        // Ex: if (dados.status() != null) { this.status = dados.status(); }
    }

    public Topico(DadosCriarTopicos dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.DataCriacao = LocalDateTime.now(); // Define a data de criação como agora
        this.status = StatusTopico.NAO_RESPONDIDO; // Define o status inicial como ABERTO
        this.autor = dados.autorId(); // Assumindo que DadosCriarTopicos tem um campo autorId
        this.curso = dados.cursoId(); // Assumindo que DadosCriarTopicos tem um campo cursoId
    }
    // Dentro da sua classe Topico.java
    public void inativar() {
        this.status = StatusTopico.FECHADO; // Ou defina um campo 'ativo' para false
    }

}

package alura.com.ForumHUB.domain.Topico;



import java.time.LocalDateTime;

public record DadosListagemTopicos(
        Long id, String titulo, String mensagem, LocalDateTime dataCriacao,
        StatusTopico status, Long autorId, Long cursoId
) {
    public DadosListagemTopicos(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(),
                topico.getDataCriacao(), topico.getStatus(),
                topico.getAutor(), topico.getCurso());
    }
}
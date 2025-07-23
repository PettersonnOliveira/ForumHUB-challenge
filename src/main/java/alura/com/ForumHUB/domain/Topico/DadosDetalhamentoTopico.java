package alura.com.ForumHUB.domain.Topico;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        Long autorId,
        Long cursoId
) {
    // Construtor que recebe a entidade Topico para mapear para o DTO
    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(),
                topico.getDataCriacao(), topico.getStatus(), topico.getCurso(), topico.getAutor());
    }
}

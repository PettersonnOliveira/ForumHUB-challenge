package alura.com.ForumHUB.domain.Topico;

import jakarta.validation.constraints.Size;

public record DadosAtualizarTopicos(
        @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
        String titulo, // Pode ser nulo se não for atualizado
        String mensagem, // Pode ser nulo se não for atualizado
        StatusTopico status // Permite atualizar o status
) {
}

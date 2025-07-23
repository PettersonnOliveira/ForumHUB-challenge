package alura.com.ForumHUB.domain.Topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosCriarTopicos(
        @NotBlank(message = "O título é obrigatório") // Título não pode ser nulo ou vazio
        @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
        String titulo,
        @NotBlank(message = "A mensagem é obrigatória") // Mensagem não pode ser nula ou vazia
        String mensagem,
        @NotNull(message = "O ID do autor é obrigatório") // O ID do autor não pode ser nulo
        Long autorId, // Assumindo que você recebe o ID do autor
        @NotNull(message = "O ID do curso é obrigatório") // O ID do curso não pode ser nulo
        Long cursoId // Assumindo que você recebe o ID do curso
) {
}

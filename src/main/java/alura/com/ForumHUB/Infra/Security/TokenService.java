package alura.com.ForumHUB.Infra.Security;

import alura.com.ForumHUB.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service // Indica que esta classe é um serviço
public class TokenService {

    @Value("${api.security.token.secret}") // Injeta o valor da variável de ambiente/propriedade 'api.security.token.secret'
    private String secret; // A chave secreta para assinar o JWT

    public String gerarToken(Usuario usuario) {
        try {
            // Define o algoritmo de assinatura com a chave secreta
            var algoritmo = Algorithm.HMAC256(secret);
            // Constrói o token JWT
            return JWT.create()
                    .withIssuer("API ForumHUB") // Emissor do token
                    .withSubject(usuario.getLogin()) // Assunto do token (geralmente o login do usuário)
                    .withExpiresAt(dataExpiracao()) // Data de expiração do token
                    .sign(algoritmo); // Assina o token com o algoritmo
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            // Define o algoritmo de verificação com a chave secreta
            var algoritmo = Algorithm.HMAC256(secret);
            // Verifica o token e retorna o assunto (subject)
            return JWT.require(algoritmo)
                    .withIssuer("API ForumHUB") // Emissor do token
                    .build()
                    .verify(tokenJWT)
                    .getSubject(); // Retorna o assunto (login do usuário)
        } catch (JWTVerificationException exception){
            // Lança exceção se o token for inválido ou expirar
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    // Método para definir a data de expiração do token (ex: 2 horas a partir de agora)
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); // Fuso horário de Brasília
    }
}
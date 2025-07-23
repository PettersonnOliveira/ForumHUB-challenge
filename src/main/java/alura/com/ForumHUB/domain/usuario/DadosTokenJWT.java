package alura.com.ForumHUB.domain.usuario;

public record DadosTokenJWT(String token) {
    public DadosTokenJWT(String token) {
        this.token = token;
    }
}

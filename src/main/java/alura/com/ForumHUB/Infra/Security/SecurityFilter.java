package alura.com.ForumHUB.Infra.Security;

import alura.com.ForumHUB.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Indica que esta é uma classe de componente do Spring
public class SecurityFilter extends OncePerRequestFilter { // Garante que o filtro seja executado uma única vez por requisição

    @Autowired
    private TokenService tokenService; // Nosso serviço para validar JWTs

    @Autowired
    private UsuarioRepository repository; // Repositório para buscar o usuário no banco de dados

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request); // Recupera o token do cabeçalho da requisição

        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT); // Valida o token e recupera o login do usuário
            var usuario = repository.findByLogin(subject); // Busca o usuário no banco de dados

            // Cria um objeto de autenticação e o define no contexto do Spring Security
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response); // Continua o fluxo da requisição
    }

    // Método auxiliar para recuperar o token do cabeçalho "Authorization"
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", ""); // Remove o prefixo "Bearer "
        }
        return null;
    }
}
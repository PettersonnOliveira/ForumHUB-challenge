package alura.com.ForumHUB.Controller;

import alura.com.ForumHUB.domain.usuario.DadosAutenticacao;
import alura.com.ForumHUB.domain.usuario.DadosTokenJWT;
import alura.com.ForumHUB.Infra.Security.TokenService;
import alura.com.ForumHUB.domain.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login") // Mapeia o endpoint de autenticação
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping // Endpoint para requisições POST de login
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        // Cria um objeto de autenticação com as credenciais do usuário
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        // Autentica o usuário usando o AuthenticationManager
        var authentication = manager.authenticate(authenticationToken);

        // Gera o token JWT para o usuário autenticado
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        // Retorna o token JWT na resposta
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}

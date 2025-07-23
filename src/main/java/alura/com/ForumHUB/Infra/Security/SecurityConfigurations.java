package alura.com.ForumHUB.Infra.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {


    @Autowired
    private SecurityFilter securityFilter;

    @Bean // Expõe o SecurityFilterChain como um Bean gerenciado pelo Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs REST sem estado
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define política de sessão como stateless
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/login").permitAll(); // Permite acesso público ao endpoint de login
                    // req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll(); // Se for usar Swagger/OpenAPI
                    req.anyRequest().authenticated(); // Todas as outras requisições exigem autenticação
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona nosso filtro JWT antes do filtro padrão de autenticação de usuário/senha
                .build();
    }

    @Bean // Expõe o AuthenticationManager como um Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean // Define o algoritmo de hash de senhas
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usando BCrypt para criptografar senhas
    }

}

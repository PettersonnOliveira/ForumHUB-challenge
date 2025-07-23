package alura.com.ForumHUB.domain.usuario;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Por enquanto, vamos retornar uma autoridade fixa.
        // Em um projeto real, você teria roles (perfis) no banco de dados.
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Por padrão, a conta não expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Por padrão, a conta não é bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Por padrão, as credenciais não expiram
    }

    @Override
    public boolean isEnabled() {
        return true; // Por padrão, o usuário está habilitado
    }
}

package alura.com.ForumHUB.Infra.Security;

import alura.com.ForumHUB.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService{
    @Autowired
        private UsuarioRepository repository; // Injeta o repositório de usuários

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            // Este método é chamado pelo Spring Security para carregar o usuário pelo nome de usuário (login)
            return repository.findByLogin(username);
        }
}

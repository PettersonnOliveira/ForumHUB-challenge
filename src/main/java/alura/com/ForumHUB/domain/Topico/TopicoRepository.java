package alura.com.ForumHUB.domain.Topico;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
   boolean existsByTitulo(String titulo);
    boolean existsById(Long id);
    boolean existsByMensagem(String mensagem);

}

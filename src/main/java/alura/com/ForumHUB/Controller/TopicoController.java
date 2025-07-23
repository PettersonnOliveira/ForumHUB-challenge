package alura.com.ForumHUB.Controller;

// Importar as classes DTO e Entidade corretamente



import alura.com.ForumHUB.domain.Topico.*;
import jakarta.validation.Valid; // Para usar @Valid nas validações
import jakarta.persistence.EntityNotFoundException; // Para lidar com entidades não encontradas

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; // Importe este de 'org.springframework'
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder; // Para criar URI de retorno no POST

@RestController
@RequestMapping("topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> criar(@RequestBody @Valid DadosCriarTopicos dados, UriComponentsBuilder uriBuilder) {
        Topico topico = new Topico(dados);
        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopicos>> listar(@PageableDefault(size = 10, sort = {"titulo"}) Pageable paginacao) {
        var topicos = topicoRepository.findAll(paginacao).map(DadosListagemTopicos::new);
        return ResponseEntity.ok(topicos);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizarTopicos dados) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado com ID: " + id));

        topico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Tópico não encontrado com ID: " + id);
        }

        Topico topico = topicoRepository.getReferenceById(id);
        topico.inativar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado com ID: " + id));

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }
}
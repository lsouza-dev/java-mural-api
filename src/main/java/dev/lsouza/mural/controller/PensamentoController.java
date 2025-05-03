package dev.lsouza.mural.controller;

import dev.lsouza.mural.models.dto.PensamentoAtualizacaoDTO;
import dev.lsouza.mural.models.dto.PensamentoCriacaoDTO;
import dev.lsouza.mural.models.dto.PensamentoExibicaoDTO;
import dev.lsouza.mural.service.PensamentoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/pensamentos")
@AllArgsConstructor
public class PensamentoController {

    private final PensamentoService pensamentoService;

    @PostMapping("/criar")
    @Transactional
    public ResponseEntity<PensamentoExibicaoDTO> criar (@RequestBody PensamentoCriacaoDTO criacaoDTO, UriComponentsBuilder uriBuilder){
        var pensamentoCriado = pensamentoService.criar(criacaoDTO);
        var uri = uriBuilder.path("/pensamento/{id}").buildAndExpand(pensamentoCriado.getId()).toUri();
        return ResponseEntity.created(uri).body(new PensamentoExibicaoDTO(pensamentoCriado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PensamentoExibicaoDTO> buscarPorId(@PathVariable Long id){
        var pensamento = pensamentoService.obterPorId(id);
        return ResponseEntity.ok(new PensamentoExibicaoDTO(pensamento));
    }

    @DeleteMapping("/excluir/{id}")
    @Transactional
    public ResponseEntity excluir (@PathVariable Long id){
        pensamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<PensamentoExibicaoDTO>> listarTodos(@RequestParam String trecho, @PageableDefault(sort = "id") Pageable pageRequest ){

        if(trecho != null && !trecho.isBlank()){
            var muraisFiltrados = pensamentoService.listarPensamentosComFiltro(pageRequest,trecho);
            return ResponseEntity.ok(muraisFiltrados);
        }

        var murais = pensamentoService.listarPensamentos(pageRequest);
        return ResponseEntity.ok(murais);
    }

    @PutMapping("/favoritar/{id}")
    @Transactional
    public ResponseEntity<PensamentoExibicaoDTO> atualizarFavorito(@PathVariable long id){
        var pensamento = pensamentoService.alterarFavorito(id);
        return  ResponseEntity.ok(pensamento);
    }

    @PutMapping("/atualizar/{id}")
    @Transactional
    public ResponseEntity<PensamentoExibicaoDTO> atualizar(@PathVariable long id, @RequestBody PensamentoAtualizacaoDTO dto){
        var pensamentoAtualizado = pensamentoService.atualizar(dto);
        return ResponseEntity.ok(pensamentoAtualizado);
    }
}

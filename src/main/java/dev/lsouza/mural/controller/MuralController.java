package dev.lsouza.mural.controller;

import dev.lsouza.mural.models.dto.MuralAtualizacaoDTO;
import dev.lsouza.mural.models.dto.MuralCriacaoDTO;
import dev.lsouza.mural.models.dto.MuralExibicaoDTO;
import dev.lsouza.mural.service.MuralService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/mural")
@AllArgsConstructor
public class MuralController {

    private final MuralService muralService;

    @PostMapping("/criar")
    @Transactional
    public ResponseEntity<MuralExibicaoDTO> criar (@RequestBody MuralCriacaoDTO criacaoDTO, UriComponentsBuilder uriBuilder){
        var muralCriado = muralService.criar(criacaoDTO);
        var uri = uriBuilder.path("/mural/{id}").buildAndExpand(muralCriado.getId()).toUri();
        return ResponseEntity.created(uri).body(new MuralExibicaoDTO(muralCriado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MuralExibicaoDTO> buscarPorId(@PathVariable Long id){
        var mural = muralService.obterPorId(id);
        return ResponseEntity.ok(mural);
    }

    @DeleteMapping("/excluir/{id}")
    @Transactional
    public ResponseEntity excluir (@PathVariable Long id){
        muralService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<MuralExibicaoDTO>> listarTodos(@RequestParam String trecho,@PageableDefault(sort = "id") Pageable pageRequest ){
        if(trecho != null && trecho.length() > 2){
            var muraisFiltrados = muralService.listarPensamentosComFiltro(pageRequest,trecho);
            return ResponseEntity.ok(muraisFiltrados);
        }

        var murais = muralService.listarPensamentos(pageRequest);
        return ResponseEntity.ok(murais);
    }

    @PutMapping("/atualizar/{id}")
    @Transactional
    public ResponseEntity<MuralExibicaoDTO> atualizar(@PathVariable long id, @RequestBody MuralAtualizacaoDTO dto){
        var muralAtualizado =muralService.atualizar(dto);
        return ResponseEntity.ok(muralAtualizado);
    }
}

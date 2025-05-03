package dev.lsouza.mural.service;

import dev.lsouza.mural.exception.BusinessException;
import dev.lsouza.mural.models.IValidacaoMural;
import dev.lsouza.mural.models.Mural;
import dev.lsouza.mural.models.dto.MuralAtualizacaoDTO;
import dev.lsouza.mural.models.dto.MuralCriacaoDTO;
import dev.lsouza.mural.models.dto.MuralExibicaoDTO;
import dev.lsouza.mural.repository.MuralRepository;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class MuralService {

    private final MuralRepository repository;

    public Mural criar(MuralCriacaoDTO dto){
        var muralExistente = repository.existsByConteudoAndAutoria(dto.conteudo(), dto.autoria());
        if(muralExistente){
            throw new BusinessException("Já existe um mural com esse conteúdo e autoria");
        }

        validarInformacoesMural(dto);


        Mural mural = new Mural(dto);
        repository.save(mural);
        return mural;
    }

    public MuralExibicaoDTO obterPorId(Long id){
        var mural = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Mural não encontrado com o ID: " + id));
        return new MuralExibicaoDTO(mural);
    }

    public void excluir(Long id){
        var mural = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Mural não encontrado com o ID: " + id));
        repository.delete(mural);
    }

    public MuralExibicaoDTO atualizar(MuralAtualizacaoDTO dto){
        if(dto.id() == null) throw new BusinessException("O ID não pode ser nulo");
        System.out.println("Mural com os dados de alteração =====>>" + dto);
        var mural = repository.findById(dto.id()).orElseThrow(() -> new NoSuchElementException("Mural com o ID %s não encontrado.".formatted(dto.id())));
        System.out.println("Mural encontrado com o ID ========>" + mural);
        mural.atualizar(dto);
        repository.save(mural);
        System.out.println("Mural atualizado: " + mural);
        return new MuralExibicaoDTO(mural);
    }

    private void validarInformacoesMural(IValidacaoMural mural) {
        if (mural.getConteudo() == null || mural.getConteudo().length() < 5) {
            throw new BusinessException("O conteúdo deve ter mais de 5 caracteres");
        }
        if (mural.getAutoria() == null || mural.getAutoria().length() < 5) {
            throw new BusinessException("A autoria deve ter mais de 5 caracteres");
        }
        if (mural.getModelo() == null) {
            throw new BusinessException("O modelo não pode ser nulo");
        }
    }

    public Page<MuralExibicaoDTO> listarPensamentosComFiltro(Pageable pageable, String trecho) {
        return repository.listarMuraisPaginadosEPorTrecho(pageable,trecho).map(MuralExibicaoDTO::new);
    }

    public Page<MuralExibicaoDTO> listarPensamentos(Pageable pageable) {
        return repository.listarMuraisPaginados(pageable).map(MuralExibicaoDTO::new);
    }

}

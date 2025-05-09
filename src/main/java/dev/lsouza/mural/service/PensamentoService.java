package dev.lsouza.mural.service;

import dev.lsouza.mural.exception.BusinessException;
import dev.lsouza.mural.models.IValidacaoMural;
import dev.lsouza.mural.models.Pensamento;
import dev.lsouza.mural.models.dto.PensamentoAtualizacaoDTO;
import dev.lsouza.mural.models.dto.PensamentoCriacaoDTO;
import dev.lsouza.mural.models.dto.PensamentoExibicaoDTO;
import dev.lsouza.mural.repository.PensamentoRepository;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class PensamentoService {

    private final PensamentoRepository repository;

    public Pensamento criar(PensamentoCriacaoDTO dto) {
        var pensamentoExistente = repository.existsByConteudoAndAutoria(dto.conteudo(), dto.autoria());
        if (pensamentoExistente) {
            throw new BusinessException("Já existe um pensamento com esse conteúdo e autoria");
        }

        validarInformacoesMural(dto);

        Pensamento pensamento = new Pensamento(dto);
        repository.save(pensamento);
        return pensamento;
    }

    public Pensamento obterPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Mural não encontrado com o ID: " + id));
    }

    public void excluir(Long id) {
        var pensamento = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Mural não encontrado com o ID: " + id));
        repository.delete(pensamento);
    }

    public PensamentoExibicaoDTO atualizar(PensamentoAtualizacaoDTO dto) {
        if (dto.id() == null) throw new BusinessException("O ID não pode ser nulo");
        var pensamento = obterPorId(dto.id());
        pensamento.atualizar(dto);
        repository.save(pensamento);
        return new PensamentoExibicaoDTO(pensamento);
    }

    private void validarInformacoesMural(IValidacaoMural pensamento) {
        if (pensamento.getConteudo() == null || pensamento.getConteudo().length() < 5) {
            throw new BusinessException("O conteúdo deve ter mais de 5 caracteres");
        }
        if (pensamento.getAutoria() == null || pensamento.getAutoria().length() < 5) {
            throw new BusinessException("A autoria deve ter mais de 5 caracteres");
        }
        if (pensamento.getModelo() == null) {
            throw new BusinessException("O modelo não pode ser nulo");
        }
    }

    public Page<PensamentoExibicaoDTO> listarPensamentosComFiltro(Pageable pageable, String trecho) {
        return repository.listarMuraisPaginadosEPorTrecho(pageable, trecho).map(PensamentoExibicaoDTO::new);
    }

    public Page<PensamentoExibicaoDTO> listarPensamentos(Pageable pageable) {
        return repository.listarMuraisPaginados(pageable).map(PensamentoExibicaoDTO::new);
    }

    public Page<PensamentoExibicaoDTO> listarPensamentosFavoritos(Pageable pageable){
        return repository.listarPensamentosFavoritos(pageable).map(PensamentoExibicaoDTO::new);

    }

    public PensamentoExibicaoDTO alterarFavorito(Long id) {
        var pensamento = obterPorId(id);
        pensamento.setFavorito(!pensamento.isFavorito());
        repository.alterarFavorito(pensamento.getId(), pensamento.isFavorito());

        return new PensamentoExibicaoDTO(pensamento);
    }
}

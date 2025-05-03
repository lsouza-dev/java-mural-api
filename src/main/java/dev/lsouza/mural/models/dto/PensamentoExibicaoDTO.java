package dev.lsouza.mural.models.dto;

import dev.lsouza.mural.models.IValidacaoMural;
import dev.lsouza.mural.models.Pensamento;

public record PensamentoExibicaoDTO(
        Long id,
        String conteudo,
        String autoria,
        String modelo,
        boolean favorito
) implements IValidacaoMural {
    public PensamentoExibicaoDTO(Pensamento m){
        this(m.getId(),m.getConteudo(),m.getAutoria(),m.getModelo().toString().toLowerCase(),m.isFavorito());
    }

    @Override
    public String getConteudo() {
        return  this.conteudo;
    }

    @Override
    public String getAutoria() {
        return  this.autoria;
    }

    @Override
    public String getModelo() {
        return this.modelo;
    }
}

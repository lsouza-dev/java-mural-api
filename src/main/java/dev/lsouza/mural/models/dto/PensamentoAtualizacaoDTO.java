package dev.lsouza.mural.models.dto;

import dev.lsouza.mural.models.IValidacaoMural;

public record PensamentoAtualizacaoDTO(
        Long id,
        String conteudo,
        String autoria,
        String modelo,
        boolean favorito
) implements IValidacaoMural{
    @Override
    public String getConteudo() {
        return this.conteudo;
    }

    @Override
    public String getAutoria() {
        return this.autoria;
    }

    @Override
    public String getModelo() {
        return this.modelo;
    }
}

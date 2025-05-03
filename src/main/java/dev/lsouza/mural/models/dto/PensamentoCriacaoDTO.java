package dev.lsouza.mural.models.dto;

import dev.lsouza.mural.models.IValidacaoMural;

public record PensamentoCriacaoDTO(
        String conteudo,
        String autoria,
        String modelo,
        boolean favorito
) implements IValidacaoMural {
    @Override
    public String getConteudo() {
        return conteudo;
    }

    @Override
    public String getAutoria() {
        return autoria;
    }

    @Override
    public String getModelo() {
        return modelo;
    }
}

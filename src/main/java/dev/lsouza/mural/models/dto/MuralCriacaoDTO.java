package dev.lsouza.mural.models.dto;

import dev.lsouza.mural.models.IValidacaoMural;
import dev.lsouza.mural.models.MuralEnum;

public record MuralCriacaoDTO(
        String conteudo,
        String autoria,
        String modelo
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

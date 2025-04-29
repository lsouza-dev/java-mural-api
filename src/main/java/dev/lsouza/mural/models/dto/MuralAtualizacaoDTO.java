package dev.lsouza.mural.models.dto;

import dev.lsouza.mural.models.IValidacaoMural;
import dev.lsouza.mural.models.MuralEnum;

public record MuralAtualizacaoDTO(
        Long id,
        String conteudo,
        String autoria,
        String modelo
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

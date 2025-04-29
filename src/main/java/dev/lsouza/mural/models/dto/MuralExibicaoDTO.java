package dev.lsouza.mural.models.dto;

import dev.lsouza.mural.models.IValidacaoMural;
import dev.lsouza.mural.models.MuralEnum;
import dev.lsouza.mural.models.Mural;

public record MuralExibicaoDTO(
        Long id,
        String conteudo,
        String autoria,
        String modelo
) implements IValidacaoMural {
    public MuralExibicaoDTO(Mural m){
        this(m.getId(),m.getConteudo(),m.getAutoria(),m.getModelo().toString().toLowerCase());
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

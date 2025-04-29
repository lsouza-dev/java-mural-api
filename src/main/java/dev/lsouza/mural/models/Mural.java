package dev.lsouza.mural.models;

import dev.lsouza.mural.models.dto.MuralAtualizacaoDTO;
import dev.lsouza.mural.models.dto.MuralCriacaoDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "murals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mural {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String conteudo;
    private String autoria;
    @Enumerated(EnumType.STRING)
    private MuralEnum modelo;

    public Mural(MuralCriacaoDTO dto) {
        this.conteudo = dto.conteudo();
        this.autoria = dto.autoria();
        this.modelo = MuralEnum.fromString(dto.getModelo());
    }

    public void atualizar(MuralAtualizacaoDTO dto) {
        if(dto.getConteudo().length() > 5) this.conteudo = dto.conteudo();
        if(dto.autoria().length() > 5) this.autoria = dto.autoria();
        if(dto.modelo() != null) this.modelo = MuralEnum.fromString(dto.getModelo());
    }
}


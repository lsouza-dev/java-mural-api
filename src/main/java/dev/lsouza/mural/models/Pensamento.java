package dev.lsouza.mural.models;

import dev.lsouza.mural.models.dto.PensamentoAtualizacaoDTO;
import dev.lsouza.mural.models.dto.PensamentoCriacaoDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "pensamentos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pensamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String conteudo;
    private String autoria;
    @Enumerated(EnumType.STRING)
    private PensamentoENUM modelo;
    private boolean favorito;

    public Pensamento(PensamentoCriacaoDTO dto) {
        this.conteudo = dto.conteudo();
        this.autoria = dto.autoria();
        this.modelo = PensamentoENUM.fromString(dto.getModelo());
        this.favorito = dto.favorito();
    }

    public void atualizar(PensamentoAtualizacaoDTO dto) {
        if(dto.getConteudo().length() > 5) this.conteudo = dto.conteudo();
        if(dto.autoria().length() > 5) this.autoria = dto.autoria();
        if(dto.modelo() != null) this.modelo = PensamentoENUM.fromString(dto.getModelo());
        if(dto.favorito() != this.favorito) this.favorito = dto.favorito();
    }

    @Override
    public String toString() {
        return "Pensamento{" +
                "id=" + id +
                ", conteudo='" + conteudo + '\'' +
                ", autoria='" + autoria + '\'' +
                ", modelo=" + modelo +
                ", favorito=" + favorito +
                '}';
    }
}


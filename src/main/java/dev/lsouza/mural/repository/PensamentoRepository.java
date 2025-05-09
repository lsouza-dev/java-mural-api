package dev.lsouza.mural.repository;

import dev.lsouza.mural.models.Pensamento;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PensamentoRepository extends JpaRepository<Pensamento,Long> {
    Boolean existsByConteudoAndAutoria(String conteudo, String autoria);

    @Query(value = """
            SELECT *
            FROM pensamentos p
            """, nativeQuery = true)
    Page<Pensamento> listarMuraisPaginados(Pageable pageable);

    @Query(value = """
        SELECT *
        FROM pensamentos p
        WHERE p.conteudo ILIKE %:trecho%
           OR p.autoria ILIKE %:trecho%
        """, nativeQuery = true)
    Page<Pensamento> listarMuraisPaginadosEPorTrecho(Pageable pageable, @Param("trecho") String trecho);

    @Query(value = """
            SELECT *
            FROM pensamentos p
            WHERE p.favorito = true
            """, nativeQuery = true)
    Page<Pensamento> listarPensamentosFavoritos(Pageable pageable);

    @Modifying
    @Query(value = """
    UPDATE pensamentos
    SET favorito = :favorito
    WHERE id = :id
    """,nativeQuery = true)
    void alterarFavorito(Long id,boolean favorito);
}

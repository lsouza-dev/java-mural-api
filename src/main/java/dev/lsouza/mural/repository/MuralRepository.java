package dev.lsouza.mural.repository;

import dev.lsouza.mural.models.Mural;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MuralRepository extends JpaRepository<Mural,Long> {
    Boolean existsByConteudoAndAutoria(String conteudo, String autoria);

    @Query("""
            SELECT m
            FROM murals m
            """)
    Page<Mural> listarMuraisPaginados(Pageable pageable);

    @Query(value = """
            SELECT *
            FROM murals m
            WHERE m.autoria ILIKE %:trecho%
            """,nativeQuery = true)
    Page<Mural> listarMuraisPaginadosEPorTrecho(Pageable pageable, @Param("trecho") String trecho);
}

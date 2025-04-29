package dev.lsouza.mural.repository;

import dev.lsouza.mural.models.Mural;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.domain.Pageable;

public interface MuralRepository extends JpaRepository<Mural,Long> {
    Boolean existsByConteudoAndAutoria(String conteudo, String autoria);

    Page<Mural> findAll(Pageable pageable);
}

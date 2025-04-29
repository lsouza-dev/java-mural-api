package dev.lsouza.mural.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import dev.lsouza.mural.exception.BusinessException;

import java.util.stream.Stream;

public enum MuralEnum {
    MODELO1,
    MODELO2,
    MODELO3;

    @JsonCreator
    public static MuralEnum fromString(java.lang.String value) {
        return Stream.of(MuralEnum.values())
                .filter(m -> m.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new BusinessException("O Modelo informado é inválido: " + value));
    }
}

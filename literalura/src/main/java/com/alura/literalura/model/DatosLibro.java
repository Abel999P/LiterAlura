package com.alura.literalura.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro (
    @JsonAlias("title") String titulo,
    @JsonAlias("authors") List<DatosAutor> autorList,
    @JsonAlias("languages") List<String> language,
    @JsonAlias("download_count") Integer descargas
) {
}

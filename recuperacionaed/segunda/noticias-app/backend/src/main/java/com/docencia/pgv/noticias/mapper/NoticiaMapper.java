package com.docencia.pgv.noticias.mapper;

import com.docencia.pgv.noticias.dominio.NoticiaRequest;
import com.docencia.pgv.noticias.model.Noticia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NoticiaMapper {

  @Mapping(target = "id", ignore = true)
  Noticia toEntity(NoticiaRequest req);

  default Noticia merge(Long id, NoticiaRequest req) {
    return new Noticia(id, req.getTitulo(), req.getContenido());
  }
}

package com.docencia.pgv.noticias.mapper;

import com.docencia.pgv.noticias.dominio.NoticiaRequest;
import com.docencia.pgv.noticias.model.Noticia;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-02T20:05:50+0000",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260128-0750, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class NoticiaMapperImpl implements NoticiaMapper {

    @Override
    public Noticia toEntity(NoticiaRequest req) {
        if ( req == null ) {
            return null;
        }

        Noticia noticia = new Noticia();

        noticia.setTitulo( req.getTitulo() );
        noticia.setContenido( req.getContenido() );

        return noticia;
    }
}

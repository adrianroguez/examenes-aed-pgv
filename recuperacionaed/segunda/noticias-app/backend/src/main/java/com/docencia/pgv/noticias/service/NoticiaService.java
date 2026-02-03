package com.docencia.pgv.noticias.service;

import com.docencia.pgv.noticias.dominio.NoticiaRequest;
import com.docencia.pgv.noticias.model.Noticia;

import java.util.List;

public interface NoticiaService {
  List<Noticia> listar();
  Noticia obtener(Long id);
  Noticia crear(NoticiaRequest req);
  Noticia actualizar(Long id, NoticiaRequest req);
  void borrar(Long id);
}

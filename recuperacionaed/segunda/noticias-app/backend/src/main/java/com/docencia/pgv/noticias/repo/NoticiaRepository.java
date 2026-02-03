package com.docencia.pgv.noticias.repo;

import com.docencia.pgv.noticias.model.Noticia;

import java.util.List;
import java.util.Optional;

public interface NoticiaRepository {
  List<Noticia> findAll();
  Optional<Noticia> findById(Long id);
  Noticia save(Noticia noticia);     // create or update
  boolean deleteById(Long id);
  void seedDemoData();
}

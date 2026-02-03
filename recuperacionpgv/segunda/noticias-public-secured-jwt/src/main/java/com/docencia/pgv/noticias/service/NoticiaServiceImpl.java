package com.docencia.pgv.noticias.service;

import com.docencia.pgv.noticias.dominio.NoticiaRequest;
import com.docencia.pgv.noticias.mapper.NoticiaMapper;
import com.docencia.pgv.noticias.model.Noticia;
import com.docencia.pgv.noticias.repo.NoticiaRepository;
import com.docencia.pgv.noticias.rest.NotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * IMPLEMENTACIÓN A REALIZAR POR EL ALUMNADO.
 *
 * Requisitos:
 * - Validar nulos/vacios en titulo y contenido (IllegalArgumentException o
 * respuesta 400).
 * - CRUD completo.
 * - Cuando no exista un id -> lanzar NotFoundException (ya existe en web).
 */
@Service
public class NoticiaServiceImpl implements NoticiaService {

  private final NoticiaRepository repo;
  private final NoticiaMapper mapper;

  public NoticiaServiceImpl(NoticiaRepository repo, NoticiaMapper mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  @Override
  public List<Noticia> listar() {
    return repo.findAll();
  }

  @Override
  public Noticia crear(NoticiaRequest request) {
    if (request.getTitulo().isEmpty() || request.getContenido().isEmpty() || request.getTitulo() == null
        || request.getContenido() == null) {
      throw new IllegalArgumentException("Los campos título y campo son obligatorios");
    }
    return repo.save(mapper.toEntity(request));
  }

  @Override
  public Noticia actualizar(Long id, NoticiaRequest request) {
    if (id == null) {
      throw new IllegalArgumentException("No existe el id");
    }
    if (!repo.findById(id).isPresent()) {
      throw new NotFoundException("No existe ninguna noticia con ese id");
    }
    repo.deleteById(id);
    Noticia actualizada = mapper.toEntity(request);
    actualizada.setId(id);
    return repo.save(actualizada);
  }

  @Override
  public void borrar(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("No existe el id");
    }
    repo.deleteById(id);
  }

  @Override
  public Noticia obtener(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("No existe el id");
    }
    return repo.findById(id).orElse(null);
  }
}

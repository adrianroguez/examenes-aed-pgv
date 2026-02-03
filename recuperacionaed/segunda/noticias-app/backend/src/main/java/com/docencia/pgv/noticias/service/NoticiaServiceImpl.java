package com.docencia.pgv.noticias.service;

import com.docencia.pgv.noticias.dominio.NoticiaRequest;
import com.docencia.pgv.noticias.model.Noticia;
import com.docencia.pgv.noticias.repo.NoticiaRepository;
import com.docencia.pgv.noticias.rest.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticiaServiceImpl implements NoticiaService {

  private final NoticiaRepository repo;

  public NoticiaServiceImpl(NoticiaRepository repo) {
    this.repo = repo;
  }

  @Override
  public List<Noticia> listar() {
    return repo.findAll();
  }

  @Override
  public Noticia obtener(Long id) {
    if (id == null) throw new IllegalArgumentException("id no puede ser null");
    return repo.findById(id).orElseThrow(() -> new NotFoundException("No existe noticia con id=" + id));
  }

  @Override
  public Noticia crear(NoticiaRequest req) {
    validateRequest(req);
    Noticia n = new Noticia(null, req.getTitulo().trim(), req.getContenido().trim());
    return repo.save(n);
  }

  @Override
  public Noticia actualizar(Long id, NoticiaRequest req) {
    if (id == null) throw new IllegalArgumentException("id no puede ser null");
    validateRequest(req);
    Noticia existing = repo.findById(id).orElseThrow(() -> new NotFoundException("No existe noticia con id=" + id));
    existing.setTitulo(req.getTitulo().trim());
    existing.setContenido(req.getContenido().trim());
    return repo.save(existing);
  }

  @Override
  public void borrar(Long id) {
    if (id == null) throw new IllegalArgumentException("id no puede ser null");
    boolean deleted = repo.deleteById(id);
    if (!deleted) throw new NotFoundException("No existe noticia con id=" + id);
  }

  private void validateRequest(NoticiaRequest req) {
    if (req == null) throw new IllegalArgumentException("request no puede ser null");
    if (req.getTitulo() == null || req.getTitulo().trim().isEmpty())
      throw new IllegalArgumentException("titulo no puede ser null/vacío");
    if (req.getContenido() == null || req.getContenido().trim().isEmpty())
      throw new IllegalArgumentException("contenido no puede ser null/vacío");
  }
}

package com.docencia.pgv.noticias.rest;

import com.docencia.pgv.noticias.dominio.NoticiaRequest;
import com.docencia.pgv.noticias.model.Noticia;
import com.docencia.pgv.noticias.service.NoticiaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/noticias")
@CrossOrigin(origins = "*")
public class PublicNoticiasController {

  private final NoticiaService service;

  public PublicNoticiasController(NoticiaService service) {
    this.service = service;
  }

  @GetMapping
  public List<Noticia> listar() {
    return service.listar();
  }

  @GetMapping("/{id}")
  public Noticia obtener(@PathVariable("id") Long id) {
    return service.obtener(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Noticia crear(@Valid @RequestBody NoticiaRequest req) {
    return service.crear(req);
  }

  @PutMapping("/{id}")
  public Noticia actualizar(@PathVariable("id") Long id, @Valid @RequestBody NoticiaRequest req) {
    return service.actualizar(id, req);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void borrar(@PathVariable("id") Long id) {
    service.borrar(id);
  }
}

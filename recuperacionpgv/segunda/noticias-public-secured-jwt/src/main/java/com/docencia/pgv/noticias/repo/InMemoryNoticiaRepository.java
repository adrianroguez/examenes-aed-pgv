package com.docencia.pgv.noticias.repo;

import com.docencia.pgv.noticias.model.Noticia;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryNoticiaRepository implements NoticiaRepository {

  private final ConcurrentHashMap<Long, Noticia> store = new ConcurrentHashMap<>();
  private final AtomicLong seq = new AtomicLong(0);

  @Override
  public List<Noticia> findAll() {
    return store.values().stream()
        .sorted(Comparator.comparing(Noticia::getId))
        .toList();
  }

  @Override
  public Optional<Noticia> findById(Long id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public Noticia save(Noticia noticia) {
    if (noticia.getId() == null) {
      long id = seq.incrementAndGet();
      noticia.setId(id);
    }
    store.put(noticia.getId(), noticia);
    return noticia;
  }

  @Override
  public boolean deleteById(Long id) {
    return store.remove(id) != null;
  }

  @Override
  public void seedDemoData() {
    if (!store.isEmpty()) return;

    save(new Noticia(null, "Bienvenida", "API pública y securizada con JWT"));
    save(new Noticia(null, "Spring", "Ejemplo para consumir endpoints v1/v2"));
    save(new Noticia(null, "Swagger", "Documentación automática con OpenAPI"));
  }
}

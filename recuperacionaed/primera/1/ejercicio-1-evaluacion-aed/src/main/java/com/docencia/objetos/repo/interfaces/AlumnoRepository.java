package com.docencia.objetos.repo.interfaces;

import java.util.List;
import java.util.Optional;

import com.docencia.objetos.domain.Alumno;

public interface AlumnoRepository {
  List<Alumno> findAll();

  Optional<Alumno> findById(Long id);

  Alumno save(Alumno alumno);

  boolean existsByEmail(String email);

  void deleteById(Long id);

  long count();
}

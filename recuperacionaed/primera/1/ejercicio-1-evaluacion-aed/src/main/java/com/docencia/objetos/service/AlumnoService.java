package com.docencia.objetos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.docencia.objetos.domain.Alumno;
import com.docencia.objetos.repo.interfaces.AlumnoRepository;

/**
 * Servicio de lógica de negocio para la gestión de alumnos.
 * Actúa como intermediario entre el controlador y el repositorio.
 */
@Service
public class AlumnoService {

  private final AlumnoRepository repo;

  public AlumnoService(AlumnoRepository repo) {
    this.repo = repo;
  }

  /**
   * Lista todos los alumnos disponibles.
   * 
   * @return Lista de alumnos.
   */
  public List<Alumno> listar() {
    return repo.findAll();
  }

  /**
   * Obtiene un alumno por su ID.
   * 
   * @param id Identificador del alumno.
   * @return Alumno si existe, null en caso contrario.
   */
  public Alumno obtener(Long id) {
    return repo.findById(id).orElse(null);
  }

  /**
   * Crea un nuevo alumno.
   * 
   * @param a Alumno a crear.
   * @return Alumno creado.
   */
  public Alumno crear(Alumno a) {
    return repo.save(a);
  }

  /**
   * Actualiza un alumno existente.
   * Asigna el ID al objeto alumno y lo guarda.
   * 
   * @param id Identificador del alumno a actualizar.
   * @param a  Datos nuevos del alumno.
   * @return Alumno actualizado.
   */
  public Alumno actualizar(Long id, Alumno a) {
    // La lógica de verificar existencia podría ir aquí, pero es más simple solo
    // establecer el ID
    a.setId(id);
    return repo.save(a);
  }

  /**
   * Borra un alumno por su ID.
   * 
   * @param id Identificador del alumno a borrar.
   */
  public void borrar(Long id) {
    repo.deleteById(id);
  }

}

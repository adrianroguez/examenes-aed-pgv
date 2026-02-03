package com.docencia.objetos.repo;

import com.docencia.objetos.domain.Alumno;
import com.docencia.objetos.domain.Rol;
import com.docencia.objetos.repo.interfaces.AlumnoRepository;
import com.docencia.objetos.repo.jpa.AlumnoEntity;
import com.docencia.objetos.repo.jpa.AlumnoJpaRepository;
import com.docencia.objetos.repo.jpa.RolEntity;
import com.docencia.objetos.repo.jpa.RolJpaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de repositorio JPA para la entidad Alumno.
 * Implementa la interfaz de dominio AlumnoRepository y utiliza los repositorios
 * JPA subyacentes.
 * Se activa solo con el perfil "h2".
 */
@Repository
@Profile("h2")
public class AlumnoRepositoryJpaAdapter implements AlumnoRepository {

  private final AlumnoJpaRepository jpa;
  private final RolJpaRepository rolJpa;

  public AlumnoRepositoryJpaAdapter(AlumnoJpaRepository jpa, RolJpaRepository rolJpa) {
    this.jpa = jpa;
    this.rolJpa = rolJpa;
  }

  /**
   * Obtiene todos los alumnos.
   * 
   * @return Lista de alumnos convertidos al dominio.
   */
  @Override
  public List<Alumno> findAll() {
    return jpa.findAll().stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  /**
   * Busca un alumno por su ID.
   * 
   * @param id Identificador del alumno.
   * @return Optional con el alumno si existe.
   */
  @Override
  public Optional<Alumno> findById(Long id) {
    return jpa.findById(id).map(this::toDomain);
  }

  /**
   * Guarda un alumno.
   * Si tiene un rol asignado, busca la entidad Rol correspondiente y la asocia.
   * 
   * @param alumno Alumno a guardar.
   * @return Alumno guardado con datos actualizados.
   */
  @Override
  public Alumno save(Alumno alumno) {
    AlumnoEntity entity = toEntity(alumno);
    if (alumno.getRol() != null && alumno.getRol().getId() != null) {
      RolEntity rolEntity = rolJpa.findById(alumno.getRol().getId())
          .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
      entity.setRol(rolEntity);
    }
    AlumnoEntity saved = jpa.save(entity);
    return toDomain(saved);
  }

  /**
   * Comprueba si existe un alumno con el email dado.
   * 
   * @param email Email a verificar.
   * @return true si existe, false en caso contrario.
   */
  @Override
  public boolean existsByEmail(String email) {
    return jpa.existsByEmail(email);
  }

  /**
   * Elimina un alumno por su ID.
   * 
   * @param id Identificador del alumno a eliminar.
   */
  @Override
  public void deleteById(Long id) {
    jpa.deleteById(id);
  }

  /**
   * Cuenta el número total de alumnos.
   * 
   * @return Número de alumnos.
   */
  @Override
  public long count() {
    return jpa.count();
  }

  /**
   * Convierte una entidad AlumnoEntity al modelo de dominio Alumno.
   * 
   * @param entity Entidad a convertir.
   * @return Objeto de dominio Alumno.
   */
  private Alumno toDomain(AlumnoEntity entity) {
    Rol rolDomain = null;
    if (entity.getRol() != null) {
      rolDomain = new Rol(entity.getRol().getId(), entity.getRol().getNombre());
    }
    return new Alumno(
        entity.getId(),
        entity.getNombre(),
        entity.getEmail(),
        entity.getCiclo(),
        rolDomain);
  }

  /**
   * Convierte un objeto de dominio Alumno a entidad AlumnoEntity.
   * Nota: El Rol se maneja separadamente en el método save().
   * 
   * @param domain Objeto de dominio.
   * @return Entidad JPA.
   */
  private AlumnoEntity toEntity(Alumno domain) {
    return new AlumnoEntity(
        domain.getId(),
        domain.getNombre(),
        domain.getEmail(),
        domain.getCiclo());
  }

}

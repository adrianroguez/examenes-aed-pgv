package com.docencia.objetos.repo.jpa;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad JPA para la tabla 'roles'.
 * Mapea la estructura de base de datos para los roles y su relación con
 * alumnos.
 */
@Entity
@Table(name = "roles")
public class RolEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String nombre;

  @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
  private List<AlumnoEntity> alumnos = new ArrayList<>();

  public RolEntity() {
  }

  public RolEntity(Long id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<AlumnoEntity> getAlumnos() {
    return alumnos;
  }

  public void setAlumnos(List<AlumnoEntity> alumnos) {
    this.alumnos = alumnos;
  }
}

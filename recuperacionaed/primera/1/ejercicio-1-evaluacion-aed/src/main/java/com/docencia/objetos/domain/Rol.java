package com.docencia.objetos.domain;

import java.util.Objects;

/**
 * Entidad de dominio que representa un Rol.
 */
public class Rol {

  private Long id;
  private String nombre;

  public Rol() {
  }

  public Rol(Long id) {
    this.id = id;
  }

  public Rol(Long id, String nombre) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Rol rol = (Rol) o;
    return Objects.equals(id, rol.id) && Objects.equals(nombre, rol.nombre);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre);
  }
}

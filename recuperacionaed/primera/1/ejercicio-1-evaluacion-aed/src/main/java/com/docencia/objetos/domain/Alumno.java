package com.docencia.objetos.domain;

/**
 * Entidad de dominio que representa un Alumno.
 */
public class Alumno {
  private Long id;
  private String nombre;
  private String email;
  private String ciclo;
  private Rol rol;

  public Alumno() {
  }

  public Alumno(Long id) {
    this.id = id;
  }

  public Alumno(Long id, String nombre, String email, String ciclo) {
    this.id = id;
    this.nombre = nombre;
    this.email = email;
    this.ciclo = ciclo;
  }

  public Alumno(Long id, String nombre, String email, String ciclo, Rol rol) {
    this.id = id;
    this.nombre = nombre;
    this.email = email;
    this.ciclo = ciclo;
    this.rol = rol;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCiclo() {
    return ciclo;
  }

  public void setCiclo(String ciclo) {
    this.ciclo = ciclo;
  }

  public Rol getRol() {
    return rol;
  }

  public void setRol(Rol rol) {
    this.rol = rol;
  }

  // Patrón Builder para construcción fluida

  public Alumno id(Long id) {
    this.id = id;
    return this;
  }

  public Alumno nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  public Alumno email(String email) {
    this.email = email;
    return this;
  }

  public Alumno ciclo(String ciclo) {
    this.ciclo = ciclo;
    return this;
  }

  public Alumno rol(Rol rol) {
    this.rol = rol;
    return this;
  }
}
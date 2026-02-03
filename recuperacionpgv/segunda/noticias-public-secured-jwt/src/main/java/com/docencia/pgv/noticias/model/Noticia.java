package com.docencia.pgv.noticias.model;

public class Noticia {
  private Long id;
  private String titulo;
  private String contenido;


  public Noticia() {}

  public Noticia(Long id, String titulo, String contenido) {
    this.id = id;
    this.titulo = titulo;
    this.contenido = contenido;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getTitulo() { return titulo; }
  public void setTitulo(String titulo) { this.titulo = titulo; }

  public String getContenido() { return contenido; }
  public void setContenido(String contenido) { this.contenido = contenido; }

}

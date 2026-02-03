package com.docencia.pgv.noticias.dominio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NoticiaRequest {

  @NotBlank(message = "El titulo no puede estar vacio")
  @Size(max = 200, message = "El titulo no puede superar 200 caracteres")
  private String titulo;

  @NotBlank(message = "El contenido no puede estar vacio")
  private String contenido;

  public NoticiaRequest() {}

  public NoticiaRequest(String titulo, String contenido) {
    this.titulo = titulo;
    this.contenido = contenido;
  }

  public String getTitulo() { return titulo; }
  public void setTitulo(String titulo) { this.titulo = titulo; }

  public String getContenido() { return contenido; }
  public void setContenido(String contenido) { this.contenido = contenido; }
}

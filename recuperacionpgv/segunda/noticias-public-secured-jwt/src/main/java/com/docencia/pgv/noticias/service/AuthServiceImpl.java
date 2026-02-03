package com.docencia.pgv.noticias.service;

import com.docencia.pgv.noticias.dominio.LoginRequest;
import com.docencia.pgv.noticias.dominio.LoginResponse;
import com.docencia.pgv.noticias.security.JwtUtil;
import org.springframework.stereotype.Service;

/**
 * IMPLEMENTACIÓN A REALIZAR POR EL ALUMNADO.
 *
 * Requisitos:
 * - Usuario en memoria: admin/admin
 * - Si credenciales incorrectas -> IllegalArgumentException (o excepción
 * controlada).
 * - Si correctas -> devolver LoginResponse con token JWT.
 */

public class AuthServiceImpl implements AuthService {

  private final JwtUtil jwtUtil;

  public AuthServiceImpl(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  public String login(String username, String password) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'login'");
  }
}

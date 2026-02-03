package com.docencia.pgv.noticias.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;

/**
 * UTILIDAD JWT A REALIZAR POR EL ALUMNADO.
 *
 * Requisitos minimos:
 * - Generar tokens firmados (HS256) con un secret.
 * - Incluir subject = username.
 * - Validar token y extraer username.
 */
@Service
/**
 * Servicio para la gestion de JSON Web Tokens (JWT).
 * Se encarga de la generacion, firma y validacion de los tokens.
 */
public class JwtUtil {

  private final SecretKey key;
  private final long expirationSeconds;

  public JwtUtil(
      @Value("${app.jwt.secret}") String base64Secret,
      @Value("${app.jwt.expiration-seconds:3600}") long expirationSeconds) {
    this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
    this.expirationSeconds = expirationSeconds;
  }

  /**
   * Genera un token JWT para un usuario autenticado.
   * Incluye informacion sobre los roles del usuario.
   *
   * @param user Detalles del usuario.
   * @return Token JWT firmado.
   */
  public String generateToken(UserDetails user) {
    Instant now = Instant.now();
    List<String> roles = user.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();

    return Jwts.builder()
        .subject(user.getUsername())
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusSeconds(expirationSeconds)))
        .claim("roles", roles)
        .signWith(key)
        .compact();
  }

  /**
   * Extrae el nombre de usuario (subject) del token.
   *
   * @param token Token JWT.
   * @return Nombre de usuario.
   */
  public String extractUsername(String token) {
    return parseAllClaims(token).getSubject();
  }

  /**
   * Valida si un token es correcto y pertenece al usuario.
   *
   * @param token Token JWT.
   * @param user  Detalles del usuario para comparar.
   * @return true si el token es valido, false en caso contrario.
   */
  public boolean isTokenValid(String token, UserDetails user) {
    Claims claims = parseAllClaims(token);
    String username = claims.getSubject();
    Date exp = claims.getExpiration();
    return username != null
        && username.equals(user.getUsername())
        && exp != null
        && exp.after(new Date());
  }

  private Claims parseAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }
}

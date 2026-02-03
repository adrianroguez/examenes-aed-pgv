package com.docencia.pgv.noticias.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * CONFIGURACIÓN DE SEGURIDAD A REALIZAR POR EL ALUMNADO.
 *
 * Objetivo:
 * - Permitir acceso público a /api/public/** y /auth/login y swagger.
 * - Proteger /api/secured/** exigiendo JWT.
 * - Añadir JwtAuthFilter en la cadena de filtros.
 *
 * Ahora mismo está en modo "permitAll" para que el proyecto compile y arranque,
 * pero LOS TESTS FALLARÁN hasta que lo securices correctamente.
 */
@Configuration
@EnableMethodSecurity
/**
 * Configuracion de seguridad de la aplicacion via Spring Security.
 * Define la cadena de filtros, la gestion de sesiones y los permisos por
 * endpoint.
 */
public class SecurityConfig {
  /**
   * Define la cadena de filtros de seguridad.
   * Configura CSRF, headers, gestion de sesiones (sin estado) y reglas de
   * autorizacion HTTP.
   *
   * @param http      Objeto HttpSecurity para configurar la seguridad.
   * @param jwtFilter Filtro personalizado para validar tokens JWT.
   * @return La cadena de filtros construida.
   * @throws Exception Si ocurre un error en la configuracion.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtFilter) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/auth/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/h2-console/**",
                "/api/public/noticias/**",
                "/error")
            .permitAll()

            .requestMatchers("/api/secured/noticias/**").authenticated()

            .anyRequest().authenticated())
        .httpBasic(httpBasic -> httpBasic.disable());

    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  /**
   * Servicio de detalles de usuario en memoria.
   * Usuarios de ejemplo (para demo / practica):
   * - admin / admin -> ROLE_ADMIN
   * - user / user -> ROLE_USER
   *
   * @param encoder Codificador de contrasenias.
   * @return UserDetailsService configurado.
   */
  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder encoder) {
    return new InMemoryUserDetailsManager(
        User.withUsername("admin").password(encoder.encode("admin")).roles("ADMIN").build(),
        User.withUsername("user").password(encoder.encode("user")).roles("USER").build());
  }

  /**
   * Bean para el codificador de contrasenias.
   * Utiliza BCrypt, un algoritmo de hashing seguro.
   *
   * @return Instancia de BCryptPasswordEncoder.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Expone el AuthenticationManager como un Bean de Spring.
   * Es necesario para realizar la autenticacion manual en el AuthController.
   *
   * @param config Configuracion de autenticacion.
   * @return AuthenticationManager configurado.
   * @throws Exception Si hay error al obtener el manager.
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}

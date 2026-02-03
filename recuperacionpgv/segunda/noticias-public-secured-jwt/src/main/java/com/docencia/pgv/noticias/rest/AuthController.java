package com.docencia.pgv.noticias.rest;

import com.docencia.pgv.noticias.dominio.LoginRequest;
import com.docencia.pgv.noticias.dominio.LoginResponse;
import com.docencia.pgv.noticias.security.JwtUtil;
import com.docencia.pgv.noticias.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtService;

  public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtService) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  /**
     * Endpoint para iniciar sesion.
     * Recibe credenciales, valida y devuelve un token JWT si son correctas.
     *
     * @param request DTO con usuario y contrasenia.
     * @return Respuesta con el token JWT y los roles del usuario.
     */
    @PostMapping("/login")
    @Operation(summary = "Login (devuelve JWT)")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails user = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(user);
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity.ok(new LoginResponse(token, roles));
    }
}

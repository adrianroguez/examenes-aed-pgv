package com.docencia.pgv.noticias.dominio;

import java.util.List;

public class LoginResponse {
  private String token;
  private String tokenType = "Bearer";
  private List<String> roles;

  public LoginResponse() {
  }

  public LoginResponse(String token, List<String> roles) {
    this.token = token;
    this.roles = roles;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public List<String> getRoles() {
    return this.roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

}

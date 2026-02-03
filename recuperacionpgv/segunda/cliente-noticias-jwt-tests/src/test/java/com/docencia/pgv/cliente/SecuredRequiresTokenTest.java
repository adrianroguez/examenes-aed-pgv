package com.docencia.pgv.cliente;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SecuredRequiresTokenTest {

  @Test
  void securedSinTokenDebeFallarTest() throws Exception {
    String baseUrl = System.getProperty("api.baseUrl", "http://localhost:8080");
    ApiClient api = new ApiClient(baseUrl);

    var res = api.get("/api/secured/noticias");
    boolean ok = res.status() == 401 || res.status() == 403;
    res.printResult(ok);

    assertTrue(ok, "Se esperaba 401 o 403 en endpoint securizado sin token");
  }
}

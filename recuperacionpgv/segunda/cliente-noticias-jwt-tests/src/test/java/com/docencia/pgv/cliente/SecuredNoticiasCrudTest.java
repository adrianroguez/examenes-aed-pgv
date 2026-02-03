package com.docencia.pgv.cliente;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecuredNoticiasCrudTest {

  private static ApiClient api;
  private static String token;
  private static Long createdId;

  @BeforeAll
  static void setup() throws Exception {
    String baseUrl = System.getProperty("api.baseUrl", "http://localhost:8080");
    String user = System.getProperty("api.username", "user");
    String pass = System.getProperty("api.password", "user");

    api = new ApiClient(baseUrl);
    System.out.println("== SECURED CRUD (max 6 puntos) ==");

    // Login
    String loginPayload = "{\"username\":\"" + user + "\",\"password\":\"" + pass + "\"}";
    var res = api.postJson("/auth/login", loginPayload);

    JsonNode json = res.json();
    boolean ok = res.status() == 200 && json != null && json.hasNonNull("token") && !json.get("token").asText().isBlank();
    res.printResult(ok);

    assertEquals(200, res.status());
    assertNotNull(json);
    token = json.get("token").asText();
    assertFalse(token.isBlank());
  }

  @Test
  @Order(1)
  void listarSecuredTest() throws Exception {
    var res = api.get("/api/secured/noticias", token);
    boolean ok = res.status() == 200 && res.json() != null && res.json().isArray();
    res.printResult(ok);

    assertEquals(200, res.status());
    assertNotNull(res.json());
    assertTrue(res.json().isArray());

    // Login+GET list: 1 punto
    GradeTracker.addSecured(1);
  }

  @Test
  @Order(2)
  void crearSecuredTest() throws Exception {
    String payload = "{\"titulo\":\"Secured - Nueva\",\"contenido\":\"Creada con JWT\"}";
    var res = api.postJson("/api/secured/noticias", payload, token);

    JsonNode json = res.json();
    boolean ok = res.status() == 201 && json != null && json.hasNonNull("id") && "Secured - Nueva".equals(json.path("titulo").asText());
    res.printResult(ok);

    assertEquals(201, res.status());
    assertNotNull(json);
    createdId = json.get("id").asLong();
    assertTrue(createdId > 0);

    // POST secured: 2 puntos
    GradeTracker.addSecured(2);
  }

  @Test
  @Order(3)
  void obtenerPorIdSecuredTest() throws Exception {
    assertNotNull(createdId);

    var res = api.get("/api/secured/noticias/" + createdId, token);
    JsonNode json = res.json();
    boolean ok = res.status() == 200 && json != null && createdId.equals(json.path("id").asLong());
    res.printResult(ok);

    assertEquals(200, res.status());
    assertNotNull(json);
    assertEquals(createdId.longValue(), json.path("id").asLong());

    GradeTracker.addSecured(1);
  }

  @Test
  @Order(4)
  void actualizarSecuredTest() throws Exception {
    assertNotNull(createdId);

    String payload = "{\"titulo\":\"Secured - Editada\",\"contenido\":\"Actualizada con JWT\"}";
    var res = api.putJson("/api/secured/noticias/" + createdId, payload, token);

    JsonNode json = res.json();
    boolean ok = res.status() == 200 && json != null && "Secured - Editada".equals(json.path("titulo").asText());
    res.printResult(ok);

    assertEquals(200, res.status());
    assertNotNull(json);
    assertEquals("Secured - Editada", json.path("titulo").asText());

    GradeTracker.addSecured(1);
  }

  @Test
  @Order(5)
  void borrarSecuredTest() throws Exception {
    assertNotNull(createdId);

    var res = api.delete("/api/secured/noticias/" + createdId, token);
    boolean ok = res.status() == 204;
    res.printResult(ok);

    assertEquals(204, res.status());

    // Comprobación adicional (no puntúa extra)
    var resGet = api.get("/api/secured/noticias/" + createdId, token);
    boolean ok2 = resGet.status() == 404;
    resGet.printResult(ok2);

    GradeTracker.addSecured(1);
  }

  @AfterAll
  static void afterAll() {
    System.out.printf("PUNTUACIÓN SECURED: %d/6%n", GradeTracker.getSecured());
    GradeTracker.printFinalGrade();
  }
}

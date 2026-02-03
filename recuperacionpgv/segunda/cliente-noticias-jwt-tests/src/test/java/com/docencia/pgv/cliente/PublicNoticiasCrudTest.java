package com.docencia.pgv.cliente;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PublicNoticiasCrudTest {

  private static ApiClient api;
  private static Long createdId;

  @BeforeAll
  static void setup() {
    String baseUrl = System.getProperty("api.baseUrl", "http://localhost:8080");
    api = new ApiClient(baseUrl);
    System.out.println("== PUBLIC CRUD (max 4 puntos) ==");
  }

  @Test
  @Order(1)
  void listarPublicoTest() throws Exception {
    var res = api.get("/api/public/noticias");
    boolean ok = res.status() == 200 && res.json() != null && res.json().isArray();
    res.printResult(ok);

    assertEquals(200, res.status());
    assertNotNull(res.json());
    assertTrue(res.json().isArray());

    GradeTracker.addPublic(1);
  }

  @Test
  @Order(2)
  void crearPublicoTest() throws Exception {
    String payload = "{\"titulo\":\"Public - Nueva\",\"contenido\":\"Creada desde test\"}";
    var res = api.postJson("/api/public/noticias", payload);

    JsonNode json = res.json();
    boolean ok = res.status() == 201 && json != null && json.hasNonNull("id") && "Public - Nueva".equals(json.path("titulo").asText());
    res.printResult(ok);

    assertEquals(201, res.status());
    assertNotNull(json);
    assertTrue(json.hasNonNull("id"));
    createdId = json.get("id").asLong();

    GradeTracker.addPublic(1);
  }

  @Test
  @Order(3)
  void actualizarPublicoTest() throws Exception {
    assertNotNull(createdId, "Debe existir un id creado en el test anterior");

    String payload = "{\"titulo\":\"Public - Editada\",\"contenido\":\"Actualizada desde test\"}";
    var res = api.putJson("/api/public/noticias/" + createdId, payload);

    JsonNode json = res.json();
    boolean ok = res.status() == 200 && json != null && createdId.equals(json.path("id").asLong()) && "Public - Editada".equals(json.path("titulo").asText());
    res.printResult(ok);

    assertEquals(200, res.status());
    assertNotNull(json);
    assertEquals(createdId.longValue(), json.path("id").asLong());
    assertEquals("Public - Editada", json.path("titulo").asText());

    GradeTracker.addPublic(1);
  }

  @Test
  @Order(4)
  void borrarPublicoTest() throws Exception {
    assertNotNull(createdId, "Debe existir un id creado en el test anterior");

    var res = api.delete("/api/public/noticias/" + createdId);
    boolean ok = res.status() == 204;
    res.printResult(ok);

    assertEquals(204, res.status());

    // Comprobación adicional: GET debería ser 404 (no puntúa extra)
    var resGet = api.get("/api/public/noticias/" + createdId);
    boolean ok2 = resGet.status() == 404;
    resGet.printResult(ok2);

    GradeTracker.addPublic(1);
  }

  @AfterAll
  static void afterAll() {
    System.out.printf("PUNTUACIÓN PUBLIC: %d/4%n", GradeTracker.getPublic());
  }
}

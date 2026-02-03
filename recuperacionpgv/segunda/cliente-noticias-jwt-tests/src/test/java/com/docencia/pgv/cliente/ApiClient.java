package com.docencia.pgv.cliente;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ApiClient {

  private final HttpClient http;
  private final ObjectMapper mapper;
  private final String baseUrl;

  public ApiClient(String baseUrl) {
    this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    this.http = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(4))
        .build();
    this.mapper = new ObjectMapper();
  }

  public String url(String path) {
    if (path.startsWith("/")) return baseUrl + path;
    return baseUrl + "/" + path;
  }

  public Response get(String path) throws IOException, InterruptedException {
    String u = url(path);
    HttpRequest req = HttpRequest.newBuilder(URI.create(u))
        .GET()
        .timeout(Duration.ofSeconds(10))
        .build();
    return send("GET", u, req);
  }

  public Response get(String path, String bearer) throws IOException, InterruptedException {
    String u = url(path);
    HttpRequest req = HttpRequest.newBuilder(URI.create(u))
        .header("Authorization", "Bearer " + bearer)
        .GET()
        .timeout(Duration.ofSeconds(10))
        .build();
    return send("GET", u, req);
  }

  public Response postJson(String path, String json) throws IOException, InterruptedException {
    String u = url(path);
    HttpRequest req = HttpRequest.newBuilder(URI.create(u))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .timeout(Duration.ofSeconds(10))
        .build();
    return send("POST", u, req);
  }

  public Response postJson(String path, String json, String bearer) throws IOException, InterruptedException {
    String u = url(path);
    HttpRequest req = HttpRequest.newBuilder(URI.create(u))
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + bearer)
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .timeout(Duration.ofSeconds(10))
        .build();
    return send("POST", u, req);
  }

  public Response putJson(String path, String json) throws IOException, InterruptedException {
    String u = url(path);
    HttpRequest req = HttpRequest.newBuilder(URI.create(u))
        .header("Content-Type", "application/json")
        .PUT(HttpRequest.BodyPublishers.ofString(json))
        .timeout(Duration.ofSeconds(10))
        .build();
    return send("PUT", u, req);
  }

  public Response putJson(String path, String json, String bearer) throws IOException, InterruptedException {
    String u = url(path);
    HttpRequest req = HttpRequest.newBuilder(URI.create(u))
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + bearer)
        .PUT(HttpRequest.BodyPublishers.ofString(json))
        .timeout(Duration.ofSeconds(10))
        .build();
    return send("PUT", u, req);
  }

  public Response delete(String path) throws IOException, InterruptedException {
    String u = url(path);
    HttpRequest req = HttpRequest.newBuilder(URI.create(u))
        .DELETE()
        .timeout(Duration.ofSeconds(10))
        .build();
    return send("DELETE", u, req);
  }

  public Response delete(String path, String bearer) throws IOException, InterruptedException {
    String u = url(path);
    HttpRequest req = HttpRequest.newBuilder(URI.create(u))
        .header("Authorization", "Bearer " + bearer)
        .DELETE()
        .timeout(Duration.ofSeconds(10))
        .build();
    return send("DELETE", u, req);
  }

  private Response send(String method, String url, HttpRequest req) throws IOException, InterruptedException {
    HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
    return new Response(method, url, res.statusCode(), res.body(), mapper);
  }

  public record Response(String method, String url, int status, String body, ObjectMapper mapper) {
    public JsonNode json() {
      try {
        if (body == null || body.isBlank()) return null;
        return mapper.readTree(body);
      } catch (Exception ex) {
        return null;
      }
    }

    public void printResult(boolean ok) {
      System.out.printf("%s %s -> %s (%d)%n", method, url, ok ? "OK" : "FAIL", status);
    }
  }
}

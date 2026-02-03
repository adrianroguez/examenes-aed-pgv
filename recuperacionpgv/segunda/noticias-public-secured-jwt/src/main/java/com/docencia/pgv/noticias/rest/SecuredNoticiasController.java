package com.docencia.pgv.noticias.rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.docencia.pgv.noticias.dominio.NoticiaRequest;
import com.docencia.pgv.noticias.model.Noticia;
import com.docencia.pgv.noticias.service.NoticiaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/secured/noticias")
public class SecuredNoticiasController {
    private final NoticiaService service;

    public SecuredNoticiasController(NoticiaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las noticias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de noticias recuperada con éxito"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public List<Noticia> getAll() {
        return service.listar();
    }

    @GetMapping("/{identificador}")
    @Operation(summary = "Obtener noticia por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticia encontrada"),
            @ApiResponse(responseCode = "404", description = "Noticia no encontrada"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public Noticia getById(@PathVariable("identificador") Long identificador) {
        return service.obtener(identificador);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una nueva noticia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Noticia creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public Noticia save(@RequestBody NoticiaRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{identificador}")
    @Operation(summary = "Actualizar tarea por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Noticia actualizada"),
            @ApiResponse(responseCode = "404", description = "Noticia no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public Noticia update(@PathVariable("identificador") Long identificador, @RequestBody NoticiaRequest request) {
        return service.actualizar(identificador, request);
    }

    @DeleteMapping("/{identificador}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar noticia por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Noticia eliminada"),
            @ApiResponse(responseCode = "404", description = "Noticia no encontrada"),
            @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public void delete(@PathVariable("identificador") Long identificador) {
        service.borrar(identificador);
    }
}

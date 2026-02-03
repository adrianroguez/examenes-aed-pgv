package com.docencia.restejercicio.controller;

import com.docencia.restejercicio.common.ApiRestController;
import com.docencia.restejercicio.model.Task;
import com.docencia.restejercicio.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiRestController("/tasks")
public class TaskController {

    private final TaskService service;

    // Constructor con inyeccion de dependencia
    public TaskController(TaskService service) {
        this.service = service;
    }

    // Endpoint para obtener todas las tareas
    @GetMapping
    public ResponseEntity<List<Task>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Endpoint para obtener una tarea por ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // Endpoint para crear una nueva tarea
    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(task));
    }

    // Endpoint para actualizar una tarea existente
    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task task) {
        return ResponseEntity.ok(service.update(id, task));
    }

    // Endpoint para eliminar una tarea
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

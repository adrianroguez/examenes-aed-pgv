package com.docencia.restejercicio.service;

import com.docencia.restejercicio.model.Task;
import com.docencia.restejercicio.repository.TaskRepository;

import java.util.List;

@org.springframework.stereotype.Service
public class TaskService {

    private final TaskRepository repository;

    // Constructor con inyeccion de dependencia
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    // Obtiene todas las tareas
    public List<Task> getAll() {
        return repository.findAll();
    }

    // Obtiene una tarea por ID
    public Task getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    // Crea una nueva tarea
    public Task create(Task task) {
        return repository.save(task);
    }

    // Actualiza una tarea existente
    public Task update(Long id, Task update) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Task not found");
        }
        // Se elimina la anterior para que el save la inserte correctamente
        repository.deleteById(id);
        update.setId(id);
        return repository.save(update);
    }

    // Elimina una tarea por ID
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Task not found");
        }
        repository.deleteById(id);
    }
}

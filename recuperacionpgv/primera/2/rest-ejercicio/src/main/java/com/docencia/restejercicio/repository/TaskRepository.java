package com.docencia.restejercicio.repository;

import com.docencia.restejercicio.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {

    // Lista en memoria para almacenar tareas
    private final List<Task> tasks = new ArrayList<>();

    // Busca todas las tareas
    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    // Busca una tarea por ID
    public Optional<Task> findById(Long id) {
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst();
    }

    // Guarda una tarea
    public Task save(Task task) {
        if (task.getId() == null) {
            // Asigna un nuevo ID calculando el maximo actual + 1
            long newId = tasks.stream().mapToLong(Task::getId).max().orElse(0L) + 1;
            task.setId(newId);
            tasks.add(task);
        } else {
            // Si ya existe, devuelve null (no sobrescribe)
            if (existsById(task.getId())) {
                return null;
            }
            tasks.add(task);
        }
        return task;
    }

    public void deleteById(Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }

    public boolean existsById(Long id) {
        return tasks.stream().anyMatch(task -> task.getId().equals(id));
    }
}

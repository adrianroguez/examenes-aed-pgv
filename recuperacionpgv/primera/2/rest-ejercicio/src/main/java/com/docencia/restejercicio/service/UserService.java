package com.docencia.restejercicio.service;

import com.docencia.restejercicio.model.User;
import com.docencia.restejercicio.repository.UserRepository;
import java.util.List;

@org.springframework.stereotype.Service
public class UserService {

    private final UserRepository repository;

    // Constructor con inyeccion de dependencia
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // Obtiene todos los usuarios
    public List<User> getAll() {
        return repository.findAll();
    }

    // Obtiene un usuario por ID
    public User getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Crea un nuevo usuario
    public User create(User user) {
        return repository.save(user);
    }

    // Actualiza un usuario existente
    public User update(Long id, User update) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        // Se elimina el anterior para que el save lo inserte correctamente
        repository.deleteById(id);
        update.setId(id);
        return repository.save(update);
    }

    // Elimina un usuario por ID
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        repository.deleteById(id);
    }
}

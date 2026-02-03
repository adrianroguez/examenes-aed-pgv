package com.docencia.restejercicio.repository;

import com.docencia.restejercicio.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    // Lista en memoria para almacenar usuarios
    private final List<User> users = new ArrayList<>();

    // Busca todos los usuarios
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    // Busca un usuario por ID
    public Optional<User> findById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    // Guarda un usuario
    public User save(User user) {
        if (user.getId() == null) {
            // Asigna un nuevo ID calculando el maximo actual + 1
            long newId = users.stream().mapToLong(User::getId).max().orElse(0L) + 1;
            user.setId(newId);
            users.add(user);
        } else {
            // Si ya existe, devuelve null (no sobrescribe)
            if (existsById(user.getId())) {
                return null;
            }
            users.add(user);
        }
        return user;
    }

    public void deleteById(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }

    public boolean existsById(Long id) {
        return users.stream().anyMatch(user -> user.getId().equals(id));
    }
}

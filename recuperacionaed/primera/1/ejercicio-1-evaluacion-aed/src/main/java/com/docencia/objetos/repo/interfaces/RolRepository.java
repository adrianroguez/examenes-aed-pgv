package com.docencia.objetos.repo.interfaces;

import java.util.List;
import java.util.Optional;

import com.docencia.objetos.domain.Rol;

public interface RolRepository {
    List<Rol> findAll();

    Optional<Rol> findById(Long id);

    Optional<Rol> findByNombre(String nombre);

    Rol save(Rol rol);

    void deleteById(Long id);

    long count();
}

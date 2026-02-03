package com.docencia.objetos.repo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolJpaRepository extends JpaRepository<RolEntity, Long> {
    boolean existsByNombre(String nombre);
}

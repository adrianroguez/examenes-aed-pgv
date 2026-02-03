package com.tiendaonline.repository;

import com.tiendaonline.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la entidad Cliente.
 * Proporciona operaciones CRUD estandar sobre la tabla CLIENTE en H2.
 */
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}

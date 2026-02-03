package com.tiendaonline.repository;

import com.tiendaonline.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la entidad Pedido.
 * Proporciona operaciones CRUD estandar sobre la tabla PEDIDO en H2.
 */
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}

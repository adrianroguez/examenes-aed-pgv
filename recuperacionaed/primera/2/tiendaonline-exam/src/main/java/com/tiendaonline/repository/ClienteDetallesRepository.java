package com.tiendaonline.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tiendaonline.model.ClienteDetalles;

/**
 * Repositorio MongoDB para el documento ClienteDetalles.
 * Permite acceder a la coleccion cliente_detalles.
 */
public interface ClienteDetallesRepository extends MongoRepository<ClienteDetalles, String> {
    /**
     * Busca los detalles de un cliente por su ID de H2.
     * 
     * @param clienteId el ID del cliente en la base de datos relacional.
     * @return un Optional que contiene los detalles si existen.
     */
    Optional<ClienteDetalles> findByClienteId(int clienteId);
}

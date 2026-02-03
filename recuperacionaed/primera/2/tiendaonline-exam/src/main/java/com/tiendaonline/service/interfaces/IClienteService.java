package com.tiendaonline.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.tiendaonline.model.Cliente;

/**
 * Interfaz para el servicio de gestion de clientes.
 */
public interface IClienteService {
    /**
     * Devuelve todos los clientes ordenados por nombre (ignorando
     * mayusculas/minusculas).
     *
     * @return lista de clientes ordenada por nombre.
     */
    List<Cliente> findAllSortedByNombre();

    /**
     * Busca un cliente por su identificador.
     *
     * @param id identificador del cliente (no puede ser null).
     * @return Optional con el cliente si existe, vacio en caso contrario.
     * @throws IllegalArgumentException si id es null.
     */
    Optional<Cliente> findById(Integer id);

    /**
     * Guarda un cliente.
     *
     * @param cliente cliente a guardar (no puede ser null).
     * @return el cliente guardado.
     * @throws IllegalArgumentException si cliente es null.
     */
    Cliente save(Cliente cliente);

    /**
     * Elimina un cliente por su identificador.
     *
     * @param id identificador del cliente (no puede ser null).
     * @throws IllegalArgumentException si id es null.
     */
    void deleteById(Integer id);

}

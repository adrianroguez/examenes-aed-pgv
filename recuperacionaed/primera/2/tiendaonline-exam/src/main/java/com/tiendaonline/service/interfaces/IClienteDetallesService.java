package com.tiendaonline.service.interfaces;

import java.util.Optional;

import com.tiendaonline.model.ClienteDetalles;

/**
 * Interfaz para el servicio de detalles de clientes en MongoDB.
 */
public interface IClienteDetallesService {
    /**
     * Guarda los detalles adicionales de un cliente en MongoDB.
     * 
     * @param detalles objeto con los detalles a guardar.
     * @return el objeto guardado.
     */
    ClienteDetalles save(ClienteDetalles detalles);

    /**
     * Busca detalles por el ID del cliente.
     * 
     * @param clienteId ID del cliente relacional.
     * @return Optional con los detalles si existen.
     */
    Optional<ClienteDetalles> findByClienteId(int clienteId);
}

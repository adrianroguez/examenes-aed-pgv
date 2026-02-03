package com.tiendaonline.service;

import com.tiendaonline.model.ClienteDetalles;
import com.tiendaonline.repository.ClienteDetallesRepository;
import com.tiendaonline.service.interfaces.IClienteDetallesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementacion del servicio de detalles de clientes (MongoDB).
 */
@Service
public class ClienteDetallesService implements IClienteDetallesService {

    @Autowired
    private ClienteDetallesRepository repository;

    @Override
    public ClienteDetalles save(ClienteDetalles detalles) {
        if (detalles == null) {
            throw new IllegalArgumentException("El detalle no puede ser nulo");
        }
        return repository.save(detalles);
    }

    @Override
    public Optional<ClienteDetalles> findByClienteId(int clienteId) {
        return repository.findByClienteId(clienteId);
    }
}

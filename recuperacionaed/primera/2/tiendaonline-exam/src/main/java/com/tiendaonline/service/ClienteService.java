package com.tiendaonline.service;

import com.tiendaonline.model.Cliente;
import com.tiendaonline.repository.ClienteRepository;
import com.tiendaonline.service.interfaces.IClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion del servicio de gestion de clientes.
 */
@Service
public class ClienteService implements IClienteService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public List<Cliente> findAllSortedByNombre() {
        // Retorna todos los clientes ordenados de forma ascendente por el nombre
        return repository.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
    }

    @Override
    public Optional<Cliente> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        return repository.findById(id);
    }

    @Override
    public Cliente save(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no puede ser nulo");
        }
        return repository.save(cliente);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        repository.deleteById(id);
    }
}

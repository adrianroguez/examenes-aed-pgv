package com.tiendaonline.controller;

import com.tiendaonline.model.Cliente;
import com.tiendaonline.model.ClienteDetalles;
import com.tiendaonline.service.interfaces.IClienteService;
import com.tiendaonline.service.interfaces.IClienteDetallesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para gestionar Clientes.
 * Expone endpoints para operaciones CRUD de Clientes y sus Detalles.
 */
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IClienteDetallesService clienteDetallesService;

    /**
     * Obtiene todos los clientes.
     * 
     * @return lista de clientes.
     */
    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.findAllSortedByNombre();
    }

    /**
     * Obtiene un cliente por su ID.
     * Incluye los detalles de MongoDB si existen.
     * 
     * @param id identificador del cliente.
     * @return respuesta con el cliente y sus detalles, o 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable Integer id) {
        Optional<Cliente> clienteOpt = clienteService.findById(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            Map<String, Object> response = new HashMap<>();
            response.put("cliente", cliente);

            // Busca si hay detalles adicionales en MongoDB
            Optional<ClienteDetalles> detallesOpt = clienteDetallesService.findByClienteId(id);
            if (detallesOpt.isPresent()) {
                response.put("detalles", detallesOpt.get());
            }

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo cliente.
     * 
     * @param cliente objeto cliente a crear.
     * @return cliente creado con estatus 201.
     */
    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        try {
            Cliente savedCliente = clienteService.save(cliente);
            return new ResponseEntity<>(savedCliente, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualiza un cliente existente.
     * 
     * @param id      identificador del cliente.
     * @param cliente datos a actualizar.
     * @return cliente actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        Optional<Cliente> existingClienteOpt = clienteService.findById(id);
        if (existingClienteOpt.isPresent()) {
            Cliente existingCliente = existingClienteOpt.get();
            existingCliente.setNombre(cliente.getNombre());
            existingCliente.setEmail(cliente.getEmail());

            Cliente updatedCliente = clienteService.save(existingCliente);
            return ResponseEntity.ok(updatedCliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un cliente.
     * 
     * @param id identificador del cliente.
     * @return estatus 204 si se elimino, 404 si no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        if (clienteService.findById(id).isPresent()) {
            clienteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Agrega detalles adicionales a un cliente existente (MongoDB).
     * 
     * @param id       identificador del cliente.
     * @param detalles objeto con los detalles.
     * @return detalles guardados con estatus 201.
     */
    @PostMapping("/{id}/detalles")
    public ResponseEntity<ClienteDetalles> addClienteDetalles(@PathVariable Integer id,
            @RequestBody ClienteDetalles detalles) {
        if (!clienteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        detalles.setClienteId(id);
        ClienteDetalles savedDetalles = clienteDetallesService.save(detalles);
        return new ResponseEntity<>(savedDetalles, HttpStatus.CREATED);
    }
}

package com.tiendaonline.service;

import com.tiendaonline.model.Cliente;
import com.tiendaonline.model.Pedido;
import com.tiendaonline.repository.ClienteRepository;
import com.tiendaonline.repository.PedidoRepository;
import com.tiendaonline.service.interfaces.IPedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementacion del servicio de gestion de pedidos.
 */
@Service
public class PedidoService implements IPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Pedido crearPedido(Integer clienteId, String estado) {
        if (clienteId == null) {
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo");
        }
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacio");
        }

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEstado(estado);
        // La fecha se asigna automaticamente al momento de creacion
        pedido.setFecha(java.time.LocalDate.now().toString());

        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
    }
}

package com.tiendaonline.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa un Pedido en la base de datos H2.
 * Esta vinculado a un Cliente.
 */
@Entity
@Table(name = "PEDIDO")
public class Pedido {
    /**
     * Identificador unico del pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Fecha de realizacion del pedido.
     */
    private String fecha;

    /**
     * Estado del pedido (ej. PENDING, SHIPPED).
     */
    private String estado;

    /**
     * Cliente que realizo el pedido.
     * Relacion Muchos a Uno.
     */
    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID")
    private Cliente cliente;

    /**
     * Constructor vacio.
     */
    public Pedido() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

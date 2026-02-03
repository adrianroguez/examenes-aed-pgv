package com.tiendaonline.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad que representa a un Cliente en la base de datos H2.
 * Contiene la informacion basica y la lista de pedidos asociados.
 */
@Entity
@Table(name = "CLIENTE")
public class Cliente {
    /**
     * Identificador unico del cliente (clave primaria).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Nombre del cliente.
     */
    private String nombre;

    /**
     * Correo electronico del cliente.
     */
    private String email;

    /**
     * Lista de pedidos realizados por el cliente.
     * Relacion Uno a Muchos mapeada por el campo "cliente" en la entidad Pedido.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();

    /**
     * Constructor vacio requerido por JPA.
     */
    public Cliente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}

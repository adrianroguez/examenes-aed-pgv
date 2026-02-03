package com.tiendaonline.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Documento de MongoDB que almacena detalles adicionales del Cliente.
 * Se vincula logicamente mediante el campo clienteId.
 */
@Document(collection = "cliente_detalles")
public class ClienteDetalles {
    /**
     * Identificador unico del documento en MongoDB.
     */
    @Id
    private String id;

    /**
     * ID del cliente en H2 al que pertenece esta informacion.
     */
    private int clienteId;

    /**
     * Numero de telefono del cliente.
     */
    private String telefono;

    /**
     * Notas internas sobre el cliente.
     */
    private String notasInternas;

    /**
     * Constructor vacio.
     */
    public ClienteDetalles() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNotasInternas() {
        return notasInternas;
    }

    public void setNotasInternas(String notasInternas) {
        this.notasInternas = notasInternas;
    }
}

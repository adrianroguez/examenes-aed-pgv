package com.docencia.com.examen.procesos.services.interfaces;

/**
 * Interfaz que define el servicio para procesar comandos.
 * Sigue el patron de diseño Service para encapsular la logica de negocio.
 */
public interface CommandService {
    /**
     * Procesa una linea de comando.
     * 
     * @param command   El comando a ejecutar (ej: "df -h")
     * @param changeCmd Indica si se debe modificar el comando (aunque no se usa
     *                  explicitamente en la implementacion actual)
     * @return true si el proceso fue exitoso, false en caso contrario.
     */
    boolean processLine(String command, boolean changeCmd);
}

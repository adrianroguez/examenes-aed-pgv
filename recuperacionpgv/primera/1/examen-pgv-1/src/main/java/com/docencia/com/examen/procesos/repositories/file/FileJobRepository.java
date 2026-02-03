package com.docencia.com.examen.procesos.repositories.file;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Repository;

import com.docencia.com.examen.procesos.repositories.interfaces.JobRepository;

/**
 * Repositorio encargado de gestionar la ubicacion del archivo de logs.
 * Implementa la interfaz JobRepository.
 * Anotado como @Repository para la gestion de dependencias de Spring.
 */
@Repository
public class FileJobRepository implements JobRepository {

    private String fileName = "logger.txt";
    private Path path;

    /**
     * Constructor por defecto.
     * Define la ruta del archivo de log.
     */
    public FileJobRepository() {
        try {
            if (fileName.isEmpty() || fileName == null) {
                this.fileName = "logger.txt";
            }
            // Define la ruta absoluta o relativa donde se guardara el log.
            // Actualmente hardcodeda a una ruta especifica del proyecto.
            path = Paths.get("src/main/java/com/docencia/com/examen/procesos/resources/logger.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Constructor con nombre de archivo parametrizable.
     * 
     * @param loggerFileName Nombre del archivo de log deseado.
     */
    public FileJobRepository(String loggerFileName) {
        try {
            if (fileName.isEmpty() || fileName == null) {
                this.fileName = "logger.txt";
            }
            this.fileName = loggerFileName;
            // A pesar de recibir un nombre diferente, aqui sigo usando la ruta hardcoded
            // por defecto en este ejemplo.
            // Deberia usar loggerFileName para construir el path dinamicamente.
            path = Paths.get("src/main/java/com/docencia/com/examen/procesos/resources/logger.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Obtiene la ruta del archivo.
     * 
     * @return Objeto Path con la ubicacion del archivo.
     */
    public Path getPath() {
        return path;
    }

    /**
     * Metodo para agregar contenido al repositorio.
     * Actualmente vacio ya que la escritura se maneja externamente mediante
     * redireccion de flujo en el ProcessBuilder.
     */
    @Override
    public boolean add(String content) {

        try {

        } catch (Exception e) {
        }
        return false;
    }
}

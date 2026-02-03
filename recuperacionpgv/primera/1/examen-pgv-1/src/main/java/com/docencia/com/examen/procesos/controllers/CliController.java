package com.docencia.com.examen.procesos.controllers;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docencia.com.examen.procesos.services.impl.DfServiceImpl;

/**
 * Controlador de la interfaz de linea de comandos (CLI).
 * Gestiona la interaccion con el usuario y delega la ejecucion de comandos a
 * los servicios.
 */
@Service
public class CliController {

    // Inyeccion del servicio especifico para el comando 'df'
    @Autowired
    public DfServiceImpl dfService;

    /**
     * Muestra el menu de consola y procesa la entrada del usuario.
     */
    public void consoleMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Lanzador de Procesos (CLI) Linux ===\n" +
                "Comandos:\n" +
                "  df -h\n" +
                "  df -H\n" +
                "  Df -H | head\n");
        // Lee la linea completa introducida por el usuario
        String commandStr = scanner.nextLine();

        // Verifica si el comando comienza con "DF" (insensible a mayusculas/minusculas)
        if (commandStr.toUpperCase().startsWith("DF"))
            // Llama al servicio para procesar la linea
            dfService.processLine(commandStr, true);

        // Cierra el scanner (buena practica para liberar recursos)
        scanner.close();
    }
}

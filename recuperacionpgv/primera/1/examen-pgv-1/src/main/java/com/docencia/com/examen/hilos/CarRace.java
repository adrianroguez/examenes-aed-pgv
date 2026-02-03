package com.docencia.com.examen.hilos;

import java.util.Random;

/**
 * Clase que representa un coche de carreras que implementa la interfaz Runnable
 * para poder ejecutarse en un hilo independiente.
 */
public class CarRace implements Runnable {
    // Nombre del coche
    private final String name;
    // Meta a alcanzar (distancia total)
    private final int goal;
    // Distancia recorrida actualmente por el coche, inicializada en 0
    private int distance = 0;
    // Variable estatica volatil que indica si ya hay un ganador.
    // Al ser estatica, es compartida por todas las instancias de la clase.
    // volatile asegura que el valor se lea siempre desde la memoria principal.
    private static volatile boolean winner = false;

    /**
     * Constructor de la clase CarRace.
     * 
     * @param name Nombre del coche.
     * @param goal Distancia meta a recorrer.
     */
    public CarRace(String name, int goal) {
        this.name = name;
        this.goal = goal;
    }

    /**
     * Metodo que se ejecuta cuando se inicia el hilo.
     * Contiene la logica de la carrera.
     */
    @Override
    public void run() {
        Random random = new Random();
        // El bucle continua mientras no se alcance la meta y no haya un ganador global
        while (distance < goal && !winner) {
            // Genera un avance aleatorio entre 1 y 10 metros
            int step = random.nextInt(10) + 1;
            distance += step;
            System.out.println(name + " avanzo " + step + " metros. Distancia total: " + distance + " metros.");

            // Comprueba si el coche ha alcanzado o superado la meta
            if (distance >= goal) {
                winner = true;
                System.out.println(name + " ha ganado la carrera.");
            }
            try {
                // Pausa el hilo durante 1000 milisegundos (1 segundo) para simular el paso del
                // tiempo
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Restaura el estado de interrupcion del hilo si es interrumpido durante el
                // sleep
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        // Creacion de los hilos para cada coche con una meta de 50 metros
        Thread coche1 = new Thread(new CarRace("Coche1", 50));
        Thread coche2 = new Thread(new CarRace("Coche2", 50));

        // Inicio de los hilos
        coche1.start();
        coche2.start();
    }
}

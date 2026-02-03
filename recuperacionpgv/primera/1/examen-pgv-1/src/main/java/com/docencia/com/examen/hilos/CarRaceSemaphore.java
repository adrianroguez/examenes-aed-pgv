package com.docencia.com.examen.hilos;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Clase que representa un coche de carreras utilizando un semaforo para
 * controlar
 * el acceso concurrente a la seccion critica (el movimiento del coche).
 */
public class CarRaceSemaphore implements Runnable {
    // Nombre del coche
    private final String name;
    // Meta a alcanzar (distancia total)
    private final int goal;
    // Distancia recorrida
    private int distance = 0;
    // Variable estatica volatil para saber si alguien ya gano
    private static volatile boolean winner = false;
    // Semaforo con solo 1 permiso, actuando como un bloqueo o mutex.
    // Esto garantiza que SOLO UN coche pueda avanzar a la vez.
    private static final Semaphore semaphore = new Semaphore(1);

    /**
     * Constructor.
     * 
     * @param name Nombre del coche.
     * @param goal Meta.
     */
    public CarRaceSemaphore(String name, int goal) {
        this.name = name;
        this.goal = goal;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (distance < goal && !winner) {
            try {
                // Adquiere el semaforo. Si otro coche lo tiene, este espera aqui.
                semaphore.acquire();

                // Verifica nuevamente si alguien ya gano despues de adquirir el semaforo
                if (winner) {
                    semaphore.release(); // Libera semaforo antes de salir
                    break;
                }

                // Seccion critica: movimiento del coche
                int step = random.nextInt(10) + 1;
                distance += step;
                System.out.println(name + " avanzo " + step + " metros. Distancia total: " + distance + " metros.");

                if (distance >= goal) {
                    winner = true;
                    System.out.println(name + " ha cruzado la meta y ha ganado");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                // IMPORTANTE: Liberar siempre el semaforo en el bloque finally
                // para evitar interbloqueos (deadlocks) si ocurre una excepcion.
                semaphore.release();
            }
            try {
                // Simula tiempo entre movimientos
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    public static void main(String[] args) {
        System.out.println("═══════════════════════════════════════");
        System.out.println("    🏁 CARRERA DE COCHES 🏁");
        System.out.println("   Rayo-McQueen vs Mate");

        System.out.println("═══════════════════════════════════════");

        int raceGoal = 50;

        // Crea los hilos (Coches)
        Thread rayoMcQueen = new Thread(new CarRaceSemaphore("Rayo-McQueen", raceGoal));
        Thread mate = new Thread(new CarRaceSemaphore("Mate", raceGoal));

        rayoMcQueen.start();
        mate.start();

        try {
            // join() hace que el hilo principal (main) espere a que terminen estos hilos
            rayoMcQueen.join();
            mate.join();

            System.out.println("\n═══════════════════════════════════════");
            System.out.println("        🏁 CARRERA TERMINADA 🏁");
            System.out.println("═══════════════════════════════════════");

        } catch (InterruptedException e) {
            System.out.println("La carrera fue interrumpida!");
        }
    }

}

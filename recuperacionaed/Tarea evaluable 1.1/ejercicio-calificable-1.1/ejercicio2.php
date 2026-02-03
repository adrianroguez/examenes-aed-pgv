<?php
declare(strict_types=1);

/**
 * Ejercicio 2. Piramide
 * 
 * Escribe una funcion que imprima por pantalla una piramide de asteriscos centrada de altura n.
 * El resultado debe seguir el patron:
 * Para n=4:
 *    *
 *   ***
 *  *****
 * *******
 */

/**
 * Imprime una piramide de asteriscos centrada de altura n.
 * 
 * @param int $n Altura de la piramide.
 * @return void No retorna nada, imprime directamente en pantalla.
 */
function imprimirPiramide(int $n): void
{
    // El ancho total de la base de la piramide es (2 * n - 1)
    $anchoTotal = (2 * $n) - 1;

    for ($i = 0; $i < $n; $i++) {
        // Numero de asteriscos en la fila actual: 1, 3, 5, ...
        // Formula: 2 * $i + 1 (donde i empieza en 0)
        $numAsteriscos = ($i * 2) + 1;

        // Numero de espacios a la izquierda para centrar
        // Formula: ($anchoTotal - $numAsteriscos) / 2
        $numEspacios = intdiv($anchoTotal - $numAsteriscos, 2);

        // Construimos la linea con espacios y asteriscos
        echo str_repeat(" ", $numEspacios);
        echo str_repeat("*", $numAsteriscos);

        // Salto de linea al final de cada fila
        echo PHP_EOL;
    }
}

// Ejemplo de uso
$n = 4;
echo "Para n=$n:\n";
imprimirPiramide($n);

echo "\n";

$n = 5;
echo "Para n=$n:\n";
imprimirPiramide($n);

echo "\n";

$n = 6;
echo "Para n=$n:\n";
imprimirPiramide($n);

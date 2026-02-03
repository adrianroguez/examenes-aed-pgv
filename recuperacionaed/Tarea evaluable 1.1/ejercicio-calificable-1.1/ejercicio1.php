<?php
declare(strict_types=1);

/**
 * Ejercicio 1. Juego FizzBuzz
 * 
 * Para 1..n, imprimir Fizz si multiplo de a, Buzz si de b, FizzBuzz si de ambos; si no, el numero.
 */


/**
 * Genera la secuencia FizzBuzz dado un numero n y dos divisores a y b.
 * 
 * @param int $n Limite superior de la secuencia.
 * @param int $a Primer divisor (Fizz).
 * @param int $b Segundo divisor (Buzz).
 * @return array Array con la secuencia resultante.
 */
function fizzBuzz(int $n, int $a, int $b): array
{
    $resultado = [];

    // Iteramos desde 1 hasta n
    for ($i = 1; $i <= $n; $i++) {
        $valor = '';

        // Verificamos si es multiplo de a
        if ($i % $a === 0) {
            $valor .= 'Fizz';
        }

        // Verificamos si es multiplo de b
        if ($i % $b === 0) {
            $valor .= 'Buzz';
        }

        // Si no es multiplo de ninguno, asignamos el numero
        if ($valor === '') {
            $valor = $i;
        }

        $resultado[] = $valor;
    }

    return $resultado;
}

// Ejemplo de uso
$n = 15;
$a = 3;
$b = 5;

// Obtenemos el resultado
$secuencia = fizzBuzz($n, $a, $b);

// Imprimimos el resultado formateado como en el ejemplo
echo "Entrada: n=$n, a=$a, b=$b\n";
echo "Salida: [" . implode(', ', array_map(fn($v) => is_string($v) ? "'$v'" : $v, $secuencia)) . "]\n";

<?php
declare(strict_types=1);

/**
 * Ejercicio 4. Calculos estadisticos
 * 
 * Dado un array de entrada obten en un array de salida con el valor min, max y suma. promedio = suma/n.
 * Entrada: [5, 2, 9]
 * Salida: [2, 9, 16, 5.3333333333]
 */

/**
 * Realiza calculos estadisticos sobre un array de numeros.
 * 
 * @param array $datos Array de numeros (int o float).
 * @return array Array con [min, max, suma, promedio].
 */
function calculosEstadisticos(array $datos): array
{
    // Verificamos si el array esta vacio para evitar division por cero
    if (empty($datos)) {
        return [];
    }

    // Calculamos el minimo
    $min = min($datos);

    // Calculamos el maximo
    $max = max($datos);

    // Calculamos la suma
    $suma = array_sum($datos);

    // Calculamos el promedio (suma / cantidad de elementos)
    $promedio = $suma / count($datos);

    return [$min, $max, $suma, $promedio];
}

// Ejemplos de uso
$casos = [
    [5, 2, 9],
    [10, -3, 7, 0]
];

foreach ($casos as $entrada) {
    $salida = calculosEstadisticos($entrada);

    echo "Entrada: [" . implode(', ', $entrada) . "]\n";
    echo "Salida: [" . implode(',', $salida) . "]\n";
}

<?php
declare(strict_types=1);

/**
 * Ejercicio 3. Duplicados
 * 
 * Dado un numero entero n, genera un nuevo numero eliminando los digitos repetidos, 
 * manteniendo el orden de la primera aparicion de cada digito al recorrer el numero 
 * de izquierda a derecha.
 */

/**
 * Elimina los digitos duplicados de un numero entero, manteniendo el orden de aparicion.
 * 
 * @param int $n Numero entero de entrada.
 * @return int Nuevo numero sin digitos repetidos.
 */
function eliminarDuplicados(int $n): int
{
    // Convertimos el numero a string para recorrer sus digitos
    $numeroStr = (string) $n;
    $resultadoStr = '';
    $digitosVistos = [];

    // Recorremos cada caracter del string
    for ($i = 0; $i < strlen($numeroStr); $i++) {
        $digito = $numeroStr[$i];

        // Si el digito no ha sido visto aun, lo agregamos al resultado y marcamos como visto
        if (!isset($digitosVistos[$digito])) {
            $resultadoStr .= $digito;
            $digitosVistos[$digito] = true;
        }
    }

    // Convertimos el string resultante de nuevo a entero
    return (int) $resultadoStr;
}

// Ejemplos de uso
$casos = [12021203, 1111, 12345];

foreach ($casos as $n) {
    $resultado = eliminarDuplicados($n);
    echo "n = $n -> $resultado\n";
}

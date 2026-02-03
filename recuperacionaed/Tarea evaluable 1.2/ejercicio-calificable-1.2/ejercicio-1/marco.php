<?php

declare(strict_types=1);

/**
 * Genera un marco rectangular con caracteres basado en las dimensiones dadas.
 *
 * @param int $alto Altura interna del marco (numero de filas vacias u ocupadas por espacio).
 * @param int $largo Ancho interno del marco (numero de espacios).
 * @param int $borde Grosor del borde del marco.
 * @return string La representacion en cadena del marco.
 */
function generarMarco(int $alto, int $largo, int $borde): string
{
    $anchoTotal = $largo + (2 * $borde);
    $altoTotal = $alto + (2 * $borde);

    $marco = "";

    // Parte superior del borde
    // Se repiten las lineas del grosor del borde
    for ($i = 0; $i < $borde; $i++) {
        $marco .= str_repeat("*", $anchoTotal) . PHP_EOL;
    }

    // Parte central
    for ($i = 0; $i < $alto; $i++) {
        $linea = "";
        // Borde izquierdo
        $linea .= str_repeat("*", $borde);
        // Espacio interno
        $linea .= str_repeat(" ", $largo);
        // Borde derecho
        $linea .= str_repeat("*", $borde);

        $marco .= $linea . PHP_EOL;
    }

    // Parte inferior del borde
    for ($i = 0; $i < $borde; $i++) {
        $marco .= str_repeat("*", $anchoTotal) . PHP_EOL;
    }

    return $marco;
}

/**
 * Procesa el archivo de entrada y genera los marcos en la salida.
 */
function procesarMarcos(): void
{
    $archivoEntrada = __DIR__ . '/marco.txt';
    $archivoSalida = __DIR__ . '/salida.txt';

    if (!file_exists($archivoEntrada)) {
        echo "Error: No se encuentra el archivo " . $archivoEntrada . PHP_EOL;
        return;
    }

    $lineas = file($archivoEntrada, FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);
    $contenidoSalida = "";

    foreach ($lineas as $linea) {
        // Parsear los valores separandolos por espacios
        $partes = explode(' ', trim($linea));

        if (count($partes) < 3) {
            echo "Error: Linea con formato incorrecto: " . $linea . PHP_EOL;
            continue;
        }

        $alto = (int)$partes[0];
        $largo = (int)$partes[1];
        $borde = (int)$partes[2];

        $marco = generarMarco($alto, $largo, $borde);

        // Mostrar en pantalla
        echo $marco . PHP_EOL;

        // Acumular para el archivo de salida
        $contenidoSalida .= $marco . PHP_EOL;
    }

    file_put_contents($archivoSalida, $contenidoSalida);
    echo "Los marcos se han guardado en " . $archivoSalida . PHP_EOL;
}

// Ejecutar el procesamiento
procesarMarcos();

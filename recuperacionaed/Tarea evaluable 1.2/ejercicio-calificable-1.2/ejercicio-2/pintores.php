<?php
declare(strict_types=1);

/**
 * Genera un marco rectangular con caracteres basado en las dimensiones dadas.
 *
 * @param int $alto Altura interna del marco.
 * @param int $largo Ancho interno del marco.
 * @param int $borde Grosor del borde del marco.
 * @return string La representacion en cadena del marco.
 */
function generarMarco(int $alto, int $largo, int $borde): string
{
    $anchoTotal = $largo + (2 * $borde);
    // El alto no se usa directamente para el loop total, sino por partes

    $marco = "";

    // Parte superior del borde
    for ($i = 0; $i < $borde; $i++) {
        $marco .= str_repeat("*", $anchoTotal) . PHP_EOL;
    }

    // Parte central
    for ($i = 0; $i < $alto; $i++) {
        $linea = "";
        $linea .= str_repeat("*", $borde);
        $linea .= str_repeat(" ", $largo);
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
 * Procesa el archivo de pintores y genera los archivos correspondientes.
 */
function procesarPintores(): void
{
    $archivoEntrada = __DIR__ . '/pintores.txt';

    if (!file_exists($archivoEntrada)) {
        echo "Error: No se encuentra el archivo " . $archivoEntrada . PHP_EOL;
        return;
    }

    $lineas = file($archivoEntrada, FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);

    foreach ($lineas as $linea) {
        $partes = explode(' ', trim($linea));

        // Se espera al menos: alto, largo, borde, nombre
        if (count($partes) < 4) {
            echo "Error: Linea con datos insuficientes: " . $linea . PHP_EOL;
            continue;
        }

        $alto = (int) $partes[0];
        $largo = (int) $partes[1];
        $borde = (int) $partes[2];

        // El nombre puede contener espacios? El ejemplo muestra una solapalabra,
        // pero por seguridad tomamos el resto.
        // Sin embargo, si explotamos por espacio, el nombre esta en el indice 3.
        $nombre = $partes[3];

        $marco = generarMarco($alto, $largo, $borde);

        // Mostrar en pantalla
        echo "Marco para " . $nombre . ":" . PHP_EOL;
        echo $marco . PHP_EOL;

        // Guardar en archivo independiente
        // Aseguramos que el nombre sea seguro para archivo (basic sanitation)
        $nombreArchivo = preg_replace('/[^a-zA-Z0-9]/', '', $nombre);
        $rutaSalida = __DIR__ . '/' . $nombreArchivo . '.txt';

        file_put_contents($rutaSalida, $marco);
        echo "Guardado en " . $nombreArchivo . ".txt" . PHP_EOL . PHP_EOL;
    }
}

procesarPintores();

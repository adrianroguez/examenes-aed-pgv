<?php
declare(strict_types=1);

/**
 * Lee las palabras y realiza el analisis solicitado.
 */
function analizarPalabras(): void
{
    $archivoEntrada = __DIR__ . '/palabras.txt';
    $archivoSalida = __DIR__ . '/resultados.txt';

    if (!file_exists($archivoEntrada)) {
        echo "Error: No se encuentra el archivo " . $archivoEntrada . PHP_EOL;
        return;
    }

    $lineas = file($archivoEntrada, FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);

    // Limpiar espacios en blanco de cada palabra
    $palabras = array_map('trim', $lineas);
    // Filtrar lineas vacias que puedan quedar despues del trim
    $palabras = array_filter($palabras, fn($p) => strlen($p) > 0);
    // Reindexar array
    $palabras = array_values($palabras);

    $total = count($palabras);
    $unicas = array_unique($palabras);
    $totalUnicas = count($unicas);

    // Encontrar palabra mas larga y mas corta
    $masLarga = "";
    $masCorta = "";

    if ($total > 0) {
        $masCorta = $palabras[0];
        foreach ($palabras as $palabra) {
            if (strlen($palabra) > strlen($masLarga)) {
                $masLarga = $palabra;
            }
            if (strlen($palabra) < strlen($masCorta)) {
                $masCorta = $palabra;
            }
        }
    }

    // Ordenar alfabeticamente (copia del array de unicas o todas?)
    // El ejemplo muestra "orden:" y luego la lista.
    // En el ejemplo aparecen duplicados: "cielo, cielo, ...".
    // Por tanto, ordenamos TODAS las palabras.
    $palabrasOrdenadas = $palabras;
    sort($palabrasOrdenadas);

    // Conteo de repeticiones
    $conteo = array_count_values($palabras);
    // Para mostrar el conteo en orden alfabetico, ordenamos el array de conteo por clave
    ksort($conteo);

    // Construir la salida
    $salida = "";
    $salida .= "total " . $total . PHP_EOL;
    $salida .= "únicas " . $totalUnicas . PHP_EOL;
    $salida .= "+larga " . $masLarga . PHP_EOL;
    $salida .= "+corta: " . $masCorta . PHP_EOL; // El ejemplo pone ": " en corta pero espacio en larga? Sigue el ejemplo.

    $salida .= "orden:" . PHP_EOL;
    foreach ($palabrasOrdenadas as $p) {
        $salida .= $p . PHP_EOL;
    }

    $salida .= "numero palabras:" . PHP_EOL;
    foreach ($conteo as $palabra => $cantidad) {
        $salida .= $palabra . ": " . $cantidad . PHP_EOL;
    }

    echo $salida;
    file_put_contents($archivoSalida, $salida);
    echo PHP_EOL . "Resultados guardados en " . $archivoSalida . PHP_EOL;
}

analizarPalabras();


# Cambios en la Adaptación a Angular 19

Este documento describe los cambios realizados para adaptar el proyecto a Angular 19, solucionando los problemas de imports y configuración.

## Cambios Principales

### 1. Configuración de `HttpClient` (src/app/app.config.ts)
**Problema:** El proyecto utiliza `HttpClient` en el servicio `NoticiasApi`, pero no estaba configurado el proveedor global, lo que causaba errores de inyección de dependencias (y problemas asociados a "imports" antiguos como `HttpClientModule`).

**Solución:** Se ha añadido `provideHttpClient(withFetch())` a la configuración de la aplicación (`appConfig`).
- `provideHttpClient()`: Es la forma moderna (Angular 15+) de proveer el cliente HTTP en aplicaciones Standalone, reemplazando al antiguo `HttpClientModule`.
- `withFetch()`: Habilita el uso de la API `fetch` nativa del navegador para las peticiones HTTP, lo cual es la recomendación estándar en Angular 19 para mejor rendimiento.

### 2. Corrección de Error de Compilación (src/app/app.config.ts)
**Problema:** El código intentaba importar `provideBrowserGlobalErrorListeners` de `@angular/core`, el cual no existe o no es necesario en esta versión.

**Solución:** Se eliminó este proveedor inválido y se sustituyó por `provideZoneChangeDetection({ eventCoalescing: true })`, que es la configuración estándar y moderna para la detección de cambios optimizada en Angular 19.

## Archivos Modificados

- `src/app/app.config.ts`:
    - Se eliminó `provideBrowserGlobalErrorListeners`.
    - Se agregó `provideZoneChangeDetection({ eventCoalescing: true })`.
    - Se mantiene `provideHttpClient(withFetch())`.

## Notas Adicionales

- El resto del código ya utilizaba componentes `standalone` y la sintaxis moderna de inyección (`inject()`), por lo que no fue necesario realizar cambios intrusivos en los componentes.
- Las dependencias en `package.json` ya indicaban la versión 19.2.0, por lo que el entorno estaba preparado para estas características.

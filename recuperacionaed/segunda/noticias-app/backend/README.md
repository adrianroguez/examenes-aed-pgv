# Backend (Spring Boot) – Noticias (SIN seguridad)

Este proyecto expone una API de noticias con rutas **públicas** (sin JWT, sin Spring Security).

<img src=images/news.png width=300>

## Endpoints

### Público
- `GET /api/public/noticias`
- `GET /api/public/noticias/{id}`
- `POST /api/public/noticias`
- `PUT /api/public/noticias/{id}`
- `DELETE /api/public/noticias/{id}`

<img src=images/swagger.png width=300>

## Swagger UI
- `/swagger-ui/index.html`

## Ejecutar
```bash
mvn clean spring-boot:run
```

La API usa un repositorio en memoria con datos de ejemplo (se inicializa al arrancar) con 3 elementos.

Crea un proyecto en angular 19.2, que te permita realizar cualquiera de las operaciones:
- Listar. `2 puntos`.
- Eliminar. `2 puntos`.
- Borrar. `2 puntos`.
- Editar y Actualizar. `2 puntos`.
- Buscar `2 puntos`.

Cada una de las operaciones debe de consultar la `api rest`en otro caso no se dara como correcta


Dieño a implementar:

<img src=images/disenio.png width=300>

> **Nota**: `El diseño se puede mofificar en colores y forma pero debe de ser totalmente funcional o se dara por incorrecto`.

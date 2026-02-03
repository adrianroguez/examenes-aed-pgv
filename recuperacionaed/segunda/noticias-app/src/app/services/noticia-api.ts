import { Injectable } from '@angular/core';
import { environment } from '../../environments/environments';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable, throwError } from 'rxjs';
import { ApiNoticia, fromApiNoticia, toApiNewNoticia } from './noticia-mapper';
import { NewNoticia, Noticia } from '../models/noticia-model';

@Injectable({
  providedIn: 'root',
})
export class NoticiasApi {
  // Url base para las peticiones a la API
  private baseUrl = `${environment.apiBaseUrl}/noticias`;

  constructor(private http: HttpClient) { }

  // Obtiene la lista de todas las noticias
  list(): Observable<Noticia[]> {
    return this.http.get<ApiNoticia[]>(this.baseUrl).pipe(
      map(array => array.map(fromApiNoticia)),
      catchError((err) => {
        // En caso de error, lo mostramos en consola y lanzamos una excepcion
        console.error(err);
        return throwError(() => new Error('No se pudo conectar con la Api de noticias.'));
      })
    )
  }

  // Obtiene una noticia especifica por su ID
  getById(id: number): Observable<Noticia> {
    return this.http.get<ApiNoticia>(`${this.baseUrl}/${id}`).pipe(
      map(fromApiNoticia),
      catchError(() => throwError(() => new Error('No se pudo obtener la noticia.')))
    )
  }

  // Crea una nueva noticia enviando los datos a la API
  create(data: NewNoticia): Observable<Noticia> {
    return this.http.post<ApiNoticia>(this.baseUrl, toApiNewNoticia(data)).pipe(
      map(fromApiNoticia),
      catchError(() => throwError(() => new Error('No se pudo crear la noticia.')))
    )
  }


  // Actualiza una noticia existente
  update(id: number, data: NewNoticia): Observable<Noticia> {
    return this.http.put<ApiNoticia>(`${this.baseUrl}/${id}`, toApiNewNoticia(data)).pipe(
      map(fromApiNoticia),
      catchError(() => throwError(() => new Error('No se pudo actualizar la noticia')))
    );
  }

  // Elimina una noticia por su ID
  remove(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`).pipe(
      catchError(() => throwError(() => new Error('No se puede eliminar la noticia')))
    )
  }
}

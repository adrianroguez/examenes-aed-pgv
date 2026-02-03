import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Noticia } from '../../models/noticia-model';
import { NoticiasApi } from '../../services/noticia-api';

@Component({
  selector: 'app-noticias',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './noticias.component.html',
  styleUrl: './noticias.component.css'
})
export class NoticiasComponent implements OnInit {
  noticias: Noticia[] = [];
  searchTerm: string = ''; // Termino de busqueda
  errorMsg: string | null = null;
  loading = false

  // Getter para filtrar las noticias en base al termino de busqueda
  get filteredNoticias(): Noticia[] {
    if (!this.searchTerm) {
      return this.noticias;
    }
    const lowerTerm = this.searchTerm.toLowerCase();
    return this.noticias.filter(n =>
      n.titulo.toLowerCase().includes(lowerTerm) ||
      n.contenido.toLowerCase().includes(lowerTerm)
    );
  }

  constructor(public api: NoticiasApi, private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.load();
  }

  load() {
    this.loading = true;
    this.errorMsg = null;

    // Llama al servicio para obtener las tareas
    this.api.list().subscribe({
      next: data => {
        this.noticias = data;
        this.loading = false;
        // Forzamos la deteccion de cambios para actualizar la vista
        this.cdr.detectChanges();
      },
      error: (e: Error) => {
        this.noticias = [];
        this.errorMsg = e.message;
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  remove(id: number) {
    this.errorMsg = null;

    this.api.remove(id).subscribe({
      next: () => this.load(),
      error: (e: Error) => this.errorMsg = e.message
    });
  }
}

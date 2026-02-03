import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NoticiasApi } from '../../services/noticia-api';

@Component({
  selector: 'app-new-noticia',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './new-noticia.component.html',
  styleUrl: './new-noticia.component.css'
})
export class NewNoticiaComponent implements OnInit {
  // Inyeccion de dependencias
  private fb = inject(FormBuilder);
  private noticias = inject(NoticiasApi);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  // Variable para almacenar el ID de la noticia si estamos editando
  editId: number | null = null;

  // Definicion del formulario reactivo con validaciones
  form = this.fb.nonNullable.group({
    titulo: this.fb.nonNullable.control('', [Validators.required, Validators.minLength(3)]),
    contenido: this.fb.nonNullable.control(''),
  });

  ngOnInit(): void {
    // Obtenemos el ID de la ruta si existe
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editId = +id;
      // Cargamos la noticia para editar
      this.noticias.getById(this.editId).subscribe({
        next: (data) => {
          // Rellenamos el formulario con los datos
          this.form.patchValue(data);
        },
        error: (err) => console.error('Error cargando noticia', err)
      });
    }
  }

  // Metodo para guardar la tarea (crear o actualizar)
  save() {
    // Si el formulario es invalido, marcamos los campos como tocados
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const data = this.form.getRawValue();

    // Si tenemos un ID, actualizamos, si no, creamos
    if (this.editId) {
      this.noticias.update(this.editId, data).subscribe({
        next: () => this.router.navigateByUrl('/'),
        error: (err) => console.error('Error actualizando noticia:', err)
      });
    } else {
      // Enviamos los datos al servicio para crear
      this.noticias.create(data).subscribe({
        next: () => {
          // Redirigimos a la lista de tareas al finalizar
          this.router.navigateByUrl('/');
        },
        error: (err) => {
          // Manejo de errores
          console.error('Error creando noticia:', err);
        }
      });
    }
  }

  // Cancela la operacion y vuelve al inicio
  cancel() {
    this.router.navigateByUrl('/');
  }
}

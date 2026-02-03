import { Routes } from '@angular/router';
import { NoticiasComponent } from './pages/noticias/noticias.component';
import { NewNoticiaComponent } from './pages/new-noticia/new-noticia.component';

export const routes: Routes = [
    { path: '', component: NoticiasComponent },
    { path: 'nueva', component: NewNoticiaComponent },
    { path: 'editar/:id', component: NewNoticiaComponent }
];

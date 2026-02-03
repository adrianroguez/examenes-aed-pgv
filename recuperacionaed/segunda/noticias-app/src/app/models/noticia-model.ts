export interface Noticia{
    id: number;
    titulo: string;
    contenido: string;
}

export type NewNoticia = Omit<Noticia, 'id'>
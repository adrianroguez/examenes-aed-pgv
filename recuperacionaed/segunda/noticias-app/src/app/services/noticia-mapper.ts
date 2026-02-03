import { Noticia, NewNoticia } from "../models/noticia-model";

export interface ApiNoticia {
    id: number;
    titulo: string;
    contenido: string;
}

export type ApiNewNoticia = Omit<ApiNoticia, 'id'>

export function fromApiNoticia(a: ApiNoticia): Noticia{
    return {...a};
}

export function toApiNewNoticia(t: NewNoticia): ApiNewNoticia {
    return {...t}
}
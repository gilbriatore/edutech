import dayjs from 'dayjs';
import { ICurso } from 'app/shared/model/curso.model';

export interface IAula {
  id?: number;
  data?: dayjs.Dayjs;
  titulo?: string;
  conteudo?: string | null;
  curso?: ICurso | null;
}

export const defaultValue: Readonly<IAula> = {};

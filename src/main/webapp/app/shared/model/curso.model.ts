import { IProfessor } from 'app/shared/model/professor.model';

export interface ICurso {
  id?: number;
  nome?: string;
  descricao?: string | null;
  cargaHoraria?: number;
  professor?: IProfessor | null;
}

export const defaultValue: Readonly<ICurso> = {};

import dayjs from 'dayjs';
import { IAluno } from 'app/shared/model/aluno.model';
import { ICurso } from 'app/shared/model/curso.model';

export interface IInscricao {
  id?: number;
  dataInscricao?: dayjs.Dayjs;
  aluno?: IAluno | null;
  curso?: ICurso | null;
}

export const defaultValue: Readonly<IInscricao> = {};

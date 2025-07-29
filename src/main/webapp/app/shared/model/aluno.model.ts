import dayjs from 'dayjs';

export interface IAluno {
  id?: number;
  nome?: string;
  email?: string;
  matricula?: string;
  dataNascimento?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IAluno> = {};

export interface IProfessor {
  id?: number;
  nome?: string;
  email?: string;
  especialidade?: string | null;
}

export const defaultValue: Readonly<IProfessor> = {};

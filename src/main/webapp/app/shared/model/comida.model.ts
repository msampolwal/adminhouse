import { IIngrediente } from 'app/shared/model//ingrediente.model';

export interface IComida {
    id?: number;
    nombre?: string;
    ingredientes?: IIngrediente[];
}

export class Comida implements IComida {
    constructor(public id?: number, public nombre?: string, public ingredientes?: IIngrediente[]) {}
}

import { IComida } from 'app/shared/model//comida.model';

export const enum TipoComida {
    DESAYUNO = 'DESAYUNO',
    ALMUERZO = 'ALMUERZO',
    MERIENDA = 'MERIENDA',
    CENA = 'CENA'
}

export interface IItemDia {
    id?: number;
    tipo?: TipoComida;
    diaId?: number;
    comidaId?: number;
    comidaNombre?: string;
    comida?: IComida;
}

export class ItemDia implements IItemDia {
    constructor(
        public id?: number,
        public tipo?: TipoComida,
        public diaId?: number,
        public comidaId?: number,
        public comidaNombre?: string,
        public comida?: IComida
    ) {}
    public toString(): string {
        return this.tipo.toString() + ':' + this.comidaNombre;
    }
}

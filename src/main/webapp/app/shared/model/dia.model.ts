import { IItemDia } from 'app/shared/model//item-dia.model';

export const enum DiaSemana {
    LUNES = 'LUNES',
    MARTES = 'MARTES',
    MIERCOLES = 'MIERCOLES',
    JUEVES = 'JUEVES',
    VIERNES = 'VIERNES',
    SABADO = 'SABADO',
    DOMINGO = 'DOMINGO'
}

export interface IDia {
    id?: number;
    diaSemana?: DiaSemana;
    grupoId?: number;
    items?: IItemDia[];
}

export class Dia implements IDia {
    constructor(public id?: number, public diaSemana?: DiaSemana, public grupoId?: number, public items?: IItemDia[]) {}
    public itemsToString(): string {
        return this.items.toString();
    }
}

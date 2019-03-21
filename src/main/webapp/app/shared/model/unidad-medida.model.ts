export interface IUnidadMedida {
    id?: number;
    nombre?: string;
}

export class UnidadMedida implements IUnidadMedida {
    constructor(public id?: number, public nombre?: string) {}
}

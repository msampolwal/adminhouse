export interface IProducto {
    id?: number;
    nombre?: string;
    unidadMedidaId?: number;
    unidadMedidaNombre?: string;
}

export class Producto implements IProducto {
    constructor(public id?: number, public nombre?: string, public unidadMedidaId?: number, public unidadMedidaNombre?: string) {}
}

export interface IPrecioProducto {
    id?: number;
    precio?: number;
    cantidad?: number;
    productoId?: number;
    unidadMedidaId?: number;
}

export class PrecioProducto implements IPrecioProducto {
    constructor(
        public id?: number,
        public precio?: number,
        public cantidad?: number,
        public productoId?: number,
        public unidadMedidaId?: number
    ) {}
}

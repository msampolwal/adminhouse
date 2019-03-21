import { IProducto } from 'app/shared/model/producto.model';

export interface IIngrediente {
    id?: number;
    cantidad?: number;
    comidaId?: number;
    productoId?: number;
    productoNombre?: string;
    producto?: IProducto;
}

export class Ingrediente implements IIngrediente {
    constructor(
        public id?: number,
        public cantidad?: number,
        public comidaId?: number,
        public productoId?: number,
        public productoNombre?: string
    ) {}
    set producto(producto: IProducto) {
        if (producto !== null) {
            this.productoNombre = producto.nombre;
            this.productoId = producto.id;
        }
    }
}

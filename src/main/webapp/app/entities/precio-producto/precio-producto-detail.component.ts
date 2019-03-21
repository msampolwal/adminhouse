import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrecioProducto } from 'app/shared/model/precio-producto.model';

@Component({
    selector: 'jhi-precio-producto-detail',
    templateUrl: './precio-producto-detail.component.html'
})
export class PrecioProductoDetailComponent implements OnInit {
    precioProducto: IPrecioProducto;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ precioProducto }) => {
            this.precioProducto = precioProducto;
        });
    }

    previousState() {
        window.history.back();
    }
}

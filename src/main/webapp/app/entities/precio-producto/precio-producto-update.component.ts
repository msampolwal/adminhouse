import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPrecioProducto } from 'app/shared/model/precio-producto.model';
import { PrecioProductoService } from './precio-producto.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';
import { UnidadMedidaService } from 'app/entities/unidad-medida';

@Component({
    selector: 'jhi-precio-producto-update',
    templateUrl: './precio-producto-update.component.html'
})
export class PrecioProductoUpdateComponent implements OnInit {
    private _precioProducto: IPrecioProducto;
    isSaving: boolean;

    productos: IProducto[];

    unidadmedidas: IUnidadMedida[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private precioProductoService: PrecioProductoService,
        private productoService: ProductoService,
        private unidadMedidaService: UnidadMedidaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ precioProducto }) => {
            this.precioProducto = precioProducto;
        });
        this.productoService.query({ filter: 'precioproducto-is-null' }).subscribe(
            (res: HttpResponse<IProducto[]>) => {
                if (!this.precioProducto.productoId) {
                    this.productos = res.body;
                } else {
                    this.productoService.find(this.precioProducto.productoId).subscribe(
                        (subRes: HttpResponse<IProducto>) => {
                            this.productos = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.unidadMedidaService.query().subscribe(
            (res: HttpResponse<IUnidadMedida[]>) => {
                this.unidadmedidas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.precioProducto.id !== undefined) {
            this.subscribeToSaveResponse(this.precioProductoService.update(this.precioProducto));
        } else {
            this.subscribeToSaveResponse(this.precioProductoService.create(this.precioProducto));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPrecioProducto>>) {
        result.subscribe((res: HttpResponse<IPrecioProducto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductoById(index: number, item: IProducto) {
        return item.id;
    }

    trackUnidadMedidaById(index: number, item: IUnidadMedida) {
        return item.id;
    }
    get precioProducto() {
        return this._precioProducto;
    }

    set precioProducto(precioProducto: IPrecioProducto) {
        this._precioProducto = precioProducto;
    }
}

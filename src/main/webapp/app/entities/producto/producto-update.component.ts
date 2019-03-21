import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from './producto.service';
import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';
import { UnidadMedidaService } from 'app/entities/unidad-medida';

@Component({
    selector: 'jhi-producto-update',
    templateUrl: './producto-update.component.html'
})
export class ProductoUpdateComponent implements OnInit {
    private _producto: IProducto;
    isSaving: boolean;

    unidadmedidas: IUnidadMedida[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private productoService: ProductoService,
        private unidadMedidaService: UnidadMedidaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ producto }) => {
            this.producto = producto;
        });
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
        if (this.producto.id !== undefined) {
            this.subscribeToSaveResponse(this.productoService.update(this.producto));
        } else {
            this.subscribeToSaveResponse(this.productoService.create(this.producto));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
        result.subscribe((res: HttpResponse<IProducto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUnidadMedidaById(index: number, item: IUnidadMedida) {
        return item.id;
    }
    get producto() {
        return this._producto;
    }

    set producto(producto: IProducto) {
        this._producto = producto;
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IIngrediente } from 'app/shared/model/ingrediente.model';
import { IngredienteService } from './ingrediente.service';
import { IComida } from 'app/shared/model/comida.model';
import { ComidaService } from 'app/entities/comida';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';

@Component({
    selector: 'jhi-ingrediente-update',
    templateUrl: './ingrediente-update.component.html'
})
export class IngredienteUpdateComponent implements OnInit {
    private _ingrediente: IIngrediente;
    isSaving: boolean;

    comidas: IComida[];

    productos: IProducto[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private ingredienteService: IngredienteService,
        private comidaService: ComidaService,
        private productoService: ProductoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ingrediente }) => {
            this.ingrediente = ingrediente;
        });
        this.comidaService.query().subscribe(
            (res: HttpResponse<IComida[]>) => {
                this.comidas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.productoService.query().subscribe(
            (res: HttpResponse<IProducto[]>) => {
                this.productos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ingrediente.id !== undefined) {
            this.subscribeToSaveResponse(this.ingredienteService.update(this.ingrediente));
        } else {
            this.subscribeToSaveResponse(this.ingredienteService.create(this.ingrediente));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIngrediente>>) {
        result.subscribe((res: HttpResponse<IIngrediente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackComidaById(index: number, item: IComida) {
        return item.id;
    }

    trackProductoById(index: number, item: IProducto) {
        return item.id;
    }
    get ingrediente() {
        return this._ingrediente;
    }

    set ingrediente(ingrediente: IIngrediente) {
        this._ingrediente = ingrediente;
    }
}

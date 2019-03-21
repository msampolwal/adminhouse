import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { FormBuilder, FormGroup } from '@angular/forms';
import { switchMap, debounceTime, tap, finalize } from 'rxjs/operators';

import { IComida } from 'app/shared/model/comida.model';
import { IIngrediente } from 'app/shared/model/ingrediente.model';
import { IProducto } from 'app/shared/model/producto.model';
import { ComidaService } from './comida.service';
import { ProductoService } from 'app/entities/producto';

@Component({
    selector: 'jhi-comida-update',
    templateUrl: './comida-update.component.html'
})
export class ComidaUpdateComponent implements OnInit {
    filteredProducts: IProducto[] = [];
    productsForm: FormGroup;
    isLoading = false;
    criteria = [];

    private _comida: IComida;
    private _ingrediente: IIngrediente;
    isSaving: boolean;
    productos: IProducto[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private comidaService: ComidaService,
        private activatedRoute: ActivatedRoute,
        private productoService: ProductoService,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ comida }) => {
            this.comida = comida;
            if (!this.comida.ingredientes) {
                this.comida.ingredientes = [];
            }
        });
        this.productoService.query().subscribe(
            (res: HttpResponse<IProducto[]>) => {
                this.productos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.ingrediente = {};

        this.productsForm = this.fb.group({
            userInput: null
        });

        this.productsForm
            .get('userInput')
            .valueChanges.pipe(
                debounceTime(300),
                tap(() => (this.isLoading = true)),
                tap(() => (this.criteria = [])),
                tap(() => this.criteria.push({ key: 'nombre.equals', value: this.productsForm.get('userInput') })),
                switchMap(value => this.productoService.query(this.criteria).pipe(finalize(() => (this.isLoading = false))))
            )
            .subscribe(
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
        if (this.comida.id !== undefined) {
            this.subscribeToSaveResponse(this.comidaService.update(this.comida));
        } else {
            this.subscribeToSaveResponse(this.comidaService.create(this.comida));
        }
    }

    selectIngrediente(ingrediente: IIngrediente) {
        this.ingrediente = ingrediente;
    }

    addIngrediente() {
        this.removeIngrediente(this.ingrediente);
        this.ingrediente.productoId = this.ingrediente.producto.id;
        this.ingrediente.productoNombre = this.ingrediente.producto.nombre;
        this.comida.ingredientes.push(this.ingrediente);
        this.ingrediente = {};
    }

    removeIngrediente(ingrediente: IIngrediente) {
        this.comida.ingredientes = this.comida.ingredientes.filter(function(e) {
            return e.productoId !== ingrediente.productoId;
        });
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IComida>>) {
        result.subscribe((res: HttpResponse<IComida>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    get comida() {
        return this._comida;
    }

    set comida(comida: IComida) {
        this._comida = comida;
    }

    get ingrediente() {
        return this._ingrediente;
    }

    set ingrediente(ingrediente: IIngrediente) {
        this._ingrediente = ingrediente;
    }
}

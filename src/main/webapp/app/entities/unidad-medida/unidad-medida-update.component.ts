import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';
import { UnidadMedidaService } from './unidad-medida.service';

@Component({
    selector: 'jhi-unidad-medida-update',
    templateUrl: './unidad-medida-update.component.html'
})
export class UnidadMedidaUpdateComponent implements OnInit {
    private _unidadMedida: IUnidadMedida;
    isSaving: boolean;

    constructor(private unidadMedidaService: UnidadMedidaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ unidadMedida }) => {
            this.unidadMedida = unidadMedida;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.unidadMedida.id !== undefined) {
            this.subscribeToSaveResponse(this.unidadMedidaService.update(this.unidadMedida));
        } else {
            this.subscribeToSaveResponse(this.unidadMedidaService.create(this.unidadMedida));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUnidadMedida>>) {
        result.subscribe((res: HttpResponse<IUnidadMedida>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get unidadMedida() {
        return this._unidadMedida;
    }

    set unidadMedida(unidadMedida: IUnidadMedida) {
        this._unidadMedida = unidadMedida;
    }
}

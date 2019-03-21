import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDia } from 'app/shared/model/dia.model';
import { IItemDia } from 'app/shared/model/item-dia.model';
import { DiaService } from './dia.service';
import { IGrupo } from 'app/shared/model/grupo.model';
import { GrupoService } from 'app/entities/grupo';
import { IComida } from 'app/shared/model/comida.model';
import { ComidaService } from 'app/entities/comida';

@Component({
    selector: 'jhi-dia-update',
    templateUrl: './dia-update.component.html'
})
export class DiaUpdateComponent implements OnInit {
    private _dia: IDia;
    private _itemDia: IItemDia;
    isSaving: boolean;

    comidas: IComida[];

    grupos: IGrupo[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private diaService: DiaService,
        private grupoService: GrupoService,
        private comidaService: ComidaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dia }) => {
            this.dia = dia;
            if (!this.dia.items) {
                this.dia.items = [];
            }
        });
        this.grupoService.query().subscribe(
            (res: HttpResponse<IGrupo[]>) => {
                this.grupos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.comidaService.query().subscribe(
            (res: HttpResponse<IComida[]>) => {
                this.comidas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.itemDia = {};
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dia.id !== undefined) {
            this.subscribeToSaveResponse(this.diaService.update(this.dia));
        } else {
            this.subscribeToSaveResponse(this.diaService.create(this.dia));
        }
    }

    selectItemDia(itemDia: IItemDia) {
        this.itemDia = itemDia;
    }

    addItemDia() {
        this.removeItemDia(this.itemDia);
        this.itemDia.comidaId = this.itemDia.comida.id;
        this.itemDia.comidaNombre = this.itemDia.comida.nombre;
        this.dia.items.push(this.itemDia);
        this.itemDia = {};
    }

    removeItemDia(itemDia: IItemDia) {
        this.dia.items = this.dia.items.filter(function(e) {
            return e.tipo.toString() !== itemDia.tipo.toString();
        });
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDia>>) {
        result.subscribe((res: HttpResponse<IDia>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGrupoById(index: number, item: IGrupo) {
        return item.id;
    }
    get dia() {
        return this._dia;
    }

    set dia(dia: IDia) {
        this._dia = dia;
    }

    get itemDia() {
        return this._itemDia;
    }

    set itemDia(itemDia: IItemDia) {
        this._itemDia = itemDia;
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IItemDia } from 'app/shared/model/item-dia.model';
import { ItemDiaService } from './item-dia.service';
import { IDia } from 'app/shared/model/dia.model';
import { DiaService } from 'app/entities/dia';
import { IComida } from 'app/shared/model/comida.model';
import { ComidaService } from 'app/entities/comida';

@Component({
    selector: 'jhi-item-dia-update',
    templateUrl: './item-dia-update.component.html'
})
export class ItemDiaUpdateComponent implements OnInit {
    private _itemDia: IItemDia;
    isSaving: boolean;

    dias: IDia[];

    comidas: IComida[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private itemDiaService: ItemDiaService,
        private diaService: DiaService,
        private comidaService: ComidaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ itemDia }) => {
            this.itemDia = itemDia;
        });
        this.diaService.query().subscribe(
            (res: HttpResponse<IDia[]>) => {
                this.dias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.comidaService.query().subscribe(
            (res: HttpResponse<IComida[]>) => {
                this.comidas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.itemDia.id !== undefined) {
            this.subscribeToSaveResponse(this.itemDiaService.update(this.itemDia));
        } else {
            this.subscribeToSaveResponse(this.itemDiaService.create(this.itemDia));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IItemDia>>) {
        result.subscribe((res: HttpResponse<IItemDia>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDiaById(index: number, item: IDia) {
        return item.id;
    }

    trackComidaById(index: number, item: IComida) {
        return item.id;
    }
    get itemDia() {
        return this._itemDia;
    }

    set itemDia(itemDia: IItemDia) {
        this._itemDia = itemDia;
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICalendarioComida } from 'app/shared/model/calendario-comida.model';
import { CalendarioComidaService } from './calendario-comida.service';
import { IGrupo } from 'app/shared/model/grupo.model';
import { GrupoService } from 'app/entities/grupo';
import { IDia } from 'app/shared/model/dia.model';
import { DiaService } from 'app/entities/dia';

@Component({
    selector: 'jhi-calendario-comida-update',
    templateUrl: './calendario-comida-update.component.html'
})
export class CalendarioComidaUpdateComponent implements OnInit {
    private _calendarioComida: ICalendarioComida;
    isSaving: boolean;

    grupos: IGrupo[];

    dias: IDia[];
    fechaDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private calendarioComidaService: CalendarioComidaService,
        private grupoService: GrupoService,
        private diaService: DiaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ calendarioComida }) => {
            this.calendarioComida = calendarioComida;
        });
        this.grupoService.query().subscribe(
            (res: HttpResponse<IGrupo[]>) => {
                this.grupos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.diaService.query().subscribe(
            (res: HttpResponse<IDia[]>) => {
                this.dias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.calendarioComida.id !== undefined) {
            this.subscribeToSaveResponse(this.calendarioComidaService.update(this.calendarioComida));
        } else {
            this.subscribeToSaveResponse(this.calendarioComidaService.create(this.calendarioComida));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICalendarioComida>>) {
        result.subscribe((res: HttpResponse<ICalendarioComida>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDiaById(index: number, item: IDia) {
        return item.id;
    }
    get calendarioComida() {
        return this._calendarioComida;
    }

    set calendarioComida(calendarioComida: ICalendarioComida) {
        this._calendarioComida = calendarioComida;
    }
}

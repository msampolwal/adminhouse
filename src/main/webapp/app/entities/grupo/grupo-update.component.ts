import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGrupo } from 'app/shared/model/grupo.model';
import { GrupoService } from './grupo.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-grupo-update',
    templateUrl: './grupo-update.component.html'
})
export class GrupoUpdateComponent implements OnInit {
    private _grupo: IGrupo;
    isSaving: boolean;

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private grupoService: GrupoService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ grupo }) => {
            this.grupo = grupo;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.grupo.id !== undefined) {
            this.subscribeToSaveResponse(this.grupoService.update(this.grupo));
        } else {
            this.subscribeToSaveResponse(this.grupoService.create(this.grupo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGrupo>>) {
        result.subscribe((res: HttpResponse<IGrupo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get grupo() {
        return this._grupo;
    }

    set grupo(grupo: IGrupo) {
        this._grupo = grupo;
    }
}

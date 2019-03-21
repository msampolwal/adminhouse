import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CalendarioComida } from 'app/shared/model/calendario-comida.model';
import { CalendarioComidaService } from './calendario-comida.service';
import { CalendarioComidaComponent } from './calendario-comida.component';
import { CalendarioComidaDetailComponent } from './calendario-comida-detail.component';
import { CalendarioComidaUpdateComponent } from './calendario-comida-update.component';
import { CalendarioComidaDeletePopupComponent } from './calendario-comida-delete-dialog.component';
import { ICalendarioComida } from 'app/shared/model/calendario-comida.model';

@Injectable({ providedIn: 'root' })
export class CalendarioComidaResolve implements Resolve<ICalendarioComida> {
    constructor(private service: CalendarioComidaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((calendarioComida: HttpResponse<CalendarioComida>) => calendarioComida.body));
        }
        return of(new CalendarioComida());
    }
}

export const calendarioComidaRoute: Routes = [
    {
        path: 'calendario-comida',
        component: CalendarioComidaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adminhouseApp.calendarioComida.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'calendario-comida/:id/view',
        component: CalendarioComidaDetailComponent,
        resolve: {
            calendarioComida: CalendarioComidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.calendarioComida.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'calendario-comida/new',
        component: CalendarioComidaUpdateComponent,
        resolve: {
            calendarioComida: CalendarioComidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.calendarioComida.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'calendario-comida/:id/edit',
        component: CalendarioComidaUpdateComponent,
        resolve: {
            calendarioComida: CalendarioComidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.calendarioComida.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const calendarioComidaPopupRoute: Routes = [
    {
        path: 'calendario-comida/:id/delete',
        component: CalendarioComidaDeletePopupComponent,
        resolve: {
            calendarioComida: CalendarioComidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.calendarioComida.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

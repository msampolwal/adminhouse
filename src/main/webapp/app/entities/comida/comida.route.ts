import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Comida } from 'app/shared/model/comida.model';
import { ComidaService } from './comida.service';
import { ComidaComponent } from './comida.component';
import { ComidaDetailComponent } from './comida-detail.component';
import { ComidaUpdateComponent } from './comida-update.component';
import { ComidaDeletePopupComponent } from './comida-delete-dialog.component';
import { IComida } from 'app/shared/model/comida.model';

@Injectable({ providedIn: 'root' })
export class ComidaResolve implements Resolve<IComida> {
    constructor(private service: ComidaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((comida: HttpResponse<Comida>) => comida.body));
        }
        return of(new Comida());
    }
}

export const comidaRoute: Routes = [
    {
        path: 'comida',
        component: ComidaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adminhouseApp.comida.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'comida/:id/view',
        component: ComidaDetailComponent,
        resolve: {
            comida: ComidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.comida.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'comida/new',
        component: ComidaUpdateComponent,
        resolve: {
            comida: ComidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.comida.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'comida/:id/edit',
        component: ComidaUpdateComponent,
        resolve: {
            comida: ComidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.comida.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const comidaPopupRoute: Routes = [
    {
        path: 'comida/:id/delete',
        component: ComidaDeletePopupComponent,
        resolve: {
            comida: ComidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.comida.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

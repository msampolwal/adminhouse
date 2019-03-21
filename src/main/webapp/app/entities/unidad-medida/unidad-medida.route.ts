import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UnidadMedida } from 'app/shared/model/unidad-medida.model';
import { UnidadMedidaService } from './unidad-medida.service';
import { UnidadMedidaComponent } from './unidad-medida.component';
import { UnidadMedidaDetailComponent } from './unidad-medida-detail.component';
import { UnidadMedidaUpdateComponent } from './unidad-medida-update.component';
import { UnidadMedidaDeletePopupComponent } from './unidad-medida-delete-dialog.component';
import { IUnidadMedida } from 'app/shared/model/unidad-medida.model';

@Injectable({ providedIn: 'root' })
export class UnidadMedidaResolve implements Resolve<IUnidadMedida> {
    constructor(private service: UnidadMedidaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((unidadMedida: HttpResponse<UnidadMedida>) => unidadMedida.body));
        }
        return of(new UnidadMedida());
    }
}

export const unidadMedidaRoute: Routes = [
    {
        path: 'unidad-medida',
        component: UnidadMedidaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adminhouseApp.unidadMedida.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unidad-medida/:id/view',
        component: UnidadMedidaDetailComponent,
        resolve: {
            unidadMedida: UnidadMedidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.unidadMedida.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unidad-medida/new',
        component: UnidadMedidaUpdateComponent,
        resolve: {
            unidadMedida: UnidadMedidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.unidadMedida.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unidad-medida/:id/edit',
        component: UnidadMedidaUpdateComponent,
        resolve: {
            unidadMedida: UnidadMedidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.unidadMedida.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const unidadMedidaPopupRoute: Routes = [
    {
        path: 'unidad-medida/:id/delete',
        component: UnidadMedidaDeletePopupComponent,
        resolve: {
            unidadMedida: UnidadMedidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.unidadMedida.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

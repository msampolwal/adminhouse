import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Grupo } from 'app/shared/model/grupo.model';
import { GrupoService } from './grupo.service';
import { GrupoComponent } from './grupo.component';
import { GrupoDetailComponent } from './grupo-detail.component';
import { GrupoUpdateComponent } from './grupo-update.component';
import { GrupoDeletePopupComponent } from './grupo-delete-dialog.component';
import { IGrupo } from 'app/shared/model/grupo.model';

@Injectable({ providedIn: 'root' })
export class GrupoResolve implements Resolve<IGrupo> {
    constructor(private service: GrupoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((grupo: HttpResponse<Grupo>) => grupo.body));
        }
        return of(new Grupo());
    }
}

export const grupoRoute: Routes = [
    {
        path: 'grupo',
        component: GrupoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adminhouseApp.grupo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grupo/:id/view',
        component: GrupoDetailComponent,
        resolve: {
            grupo: GrupoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.grupo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grupo/new',
        component: GrupoUpdateComponent,
        resolve: {
            grupo: GrupoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.grupo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'grupo/:id/edit',
        component: GrupoUpdateComponent,
        resolve: {
            grupo: GrupoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.grupo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const grupoPopupRoute: Routes = [
    {
        path: 'grupo/:id/delete',
        component: GrupoDeletePopupComponent,
        resolve: {
            grupo: GrupoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.grupo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Ingrediente } from 'app/shared/model/ingrediente.model';
import { IngredienteService } from './ingrediente.service';
import { IngredienteComponent } from './ingrediente.component';
import { IngredienteDetailComponent } from './ingrediente-detail.component';
import { IngredienteUpdateComponent } from './ingrediente-update.component';
import { IngredienteDeletePopupComponent } from './ingrediente-delete-dialog.component';
import { IIngrediente } from 'app/shared/model/ingrediente.model';

@Injectable({ providedIn: 'root' })
export class IngredienteResolve implements Resolve<IIngrediente> {
    constructor(private service: IngredienteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((ingrediente: HttpResponse<Ingrediente>) => ingrediente.body));
        }
        return of(new Ingrediente());
    }
}

export const ingredienteRoute: Routes = [
    {
        path: 'ingrediente',
        component: IngredienteComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adminhouseApp.ingrediente.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ingrediente/:id/view',
        component: IngredienteDetailComponent,
        resolve: {
            ingrediente: IngredienteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.ingrediente.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ingrediente/new',
        component: IngredienteUpdateComponent,
        resolve: {
            ingrediente: IngredienteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.ingrediente.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ingrediente/:id/edit',
        component: IngredienteUpdateComponent,
        resolve: {
            ingrediente: IngredienteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.ingrediente.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ingredientePopupRoute: Routes = [
    {
        path: 'ingrediente/:id/delete',
        component: IngredienteDeletePopupComponent,
        resolve: {
            ingrediente: IngredienteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.ingrediente.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

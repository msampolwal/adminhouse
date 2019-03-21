import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Dia } from 'app/shared/model/dia.model';
import { DiaService } from './dia.service';
import { DiaComponent } from './dia.component';
import { DiaDetailComponent } from './dia-detail.component';
import { DiaUpdateComponent } from './dia-update.component';
import { DiaDeletePopupComponent } from './dia-delete-dialog.component';
import { IDia } from 'app/shared/model/dia.model';

@Injectable({ providedIn: 'root' })
export class DiaResolve implements Resolve<IDia> {
    constructor(private service: DiaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((dia: HttpResponse<Dia>) => dia.body));
        }
        return of(new Dia());
    }
}

export const diaRoute: Routes = [
    {
        path: 'dia',
        component: DiaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adminhouseApp.dia.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dia/:id/view',
        component: DiaDetailComponent,
        resolve: {
            dia: DiaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.dia.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dia/new',
        component: DiaUpdateComponent,
        resolve: {
            dia: DiaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.dia.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dia/:id/edit',
        component: DiaUpdateComponent,
        resolve: {
            dia: DiaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.dia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const diaPopupRoute: Routes = [
    {
        path: 'dia/:id/delete',
        component: DiaDeletePopupComponent,
        resolve: {
            dia: DiaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.dia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PrecioProducto } from 'app/shared/model/precio-producto.model';
import { PrecioProductoService } from './precio-producto.service';
import { PrecioProductoComponent } from './precio-producto.component';
import { PrecioProductoDetailComponent } from './precio-producto-detail.component';
import { PrecioProductoUpdateComponent } from './precio-producto-update.component';
import { PrecioProductoDeletePopupComponent } from './precio-producto-delete-dialog.component';
import { IPrecioProducto } from 'app/shared/model/precio-producto.model';

@Injectable({ providedIn: 'root' })
export class PrecioProductoResolve implements Resolve<IPrecioProducto> {
    constructor(private service: PrecioProductoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((precioProducto: HttpResponse<PrecioProducto>) => precioProducto.body));
        }
        return of(new PrecioProducto());
    }
}

export const precioProductoRoute: Routes = [
    {
        path: 'precio-producto',
        component: PrecioProductoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adminhouseApp.precioProducto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'precio-producto/:id/view',
        component: PrecioProductoDetailComponent,
        resolve: {
            precioProducto: PrecioProductoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.precioProducto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'precio-producto/new',
        component: PrecioProductoUpdateComponent,
        resolve: {
            precioProducto: PrecioProductoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.precioProducto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'precio-producto/:id/edit',
        component: PrecioProductoUpdateComponent,
        resolve: {
            precioProducto: PrecioProductoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.precioProducto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const precioProductoPopupRoute: Routes = [
    {
        path: 'precio-producto/:id/delete',
        component: PrecioProductoDeletePopupComponent,
        resolve: {
            precioProducto: PrecioProductoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.precioProducto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ItemDia } from 'app/shared/model/item-dia.model';
import { ItemDiaService } from './item-dia.service';
import { ItemDiaComponent } from './item-dia.component';
import { ItemDiaDetailComponent } from './item-dia-detail.component';
import { ItemDiaUpdateComponent } from './item-dia-update.component';
import { ItemDiaDeletePopupComponent } from './item-dia-delete-dialog.component';
import { IItemDia } from 'app/shared/model/item-dia.model';

@Injectable({ providedIn: 'root' })
export class ItemDiaResolve implements Resolve<IItemDia> {
    constructor(private service: ItemDiaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((itemDia: HttpResponse<ItemDia>) => itemDia.body));
        }
        return of(new ItemDia());
    }
}

export const itemDiaRoute: Routes = [
    {
        path: 'item-dia',
        component: ItemDiaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'adminhouseApp.itemDia.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'item-dia/:id/view',
        component: ItemDiaDetailComponent,
        resolve: {
            itemDia: ItemDiaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.itemDia.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'item-dia/new',
        component: ItemDiaUpdateComponent,
        resolve: {
            itemDia: ItemDiaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.itemDia.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'item-dia/:id/edit',
        component: ItemDiaUpdateComponent,
        resolve: {
            itemDia: ItemDiaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.itemDia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemDiaPopupRoute: Routes = [
    {
        path: 'item-dia/:id/delete',
        component: ItemDiaDeletePopupComponent,
        resolve: {
            itemDia: ItemDiaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminhouseApp.itemDia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

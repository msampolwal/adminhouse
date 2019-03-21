import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminhouseSharedModule } from 'app/shared';
import {
    PrecioProductoComponent,
    PrecioProductoDetailComponent,
    PrecioProductoUpdateComponent,
    PrecioProductoDeletePopupComponent,
    PrecioProductoDeleteDialogComponent,
    precioProductoRoute,
    precioProductoPopupRoute
} from './';

const ENTITY_STATES = [...precioProductoRoute, ...precioProductoPopupRoute];

@NgModule({
    imports: [AdminhouseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PrecioProductoComponent,
        PrecioProductoDetailComponent,
        PrecioProductoUpdateComponent,
        PrecioProductoDeleteDialogComponent,
        PrecioProductoDeletePopupComponent
    ],
    entryComponents: [
        PrecioProductoComponent,
        PrecioProductoUpdateComponent,
        PrecioProductoDeleteDialogComponent,
        PrecioProductoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminhousePrecioProductoModule {}

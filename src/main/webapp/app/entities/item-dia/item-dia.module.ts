import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminhouseSharedModule } from 'app/shared';
import {
    ItemDiaComponent,
    ItemDiaDetailComponent,
    ItemDiaUpdateComponent,
    ItemDiaDeletePopupComponent,
    ItemDiaDeleteDialogComponent,
    itemDiaRoute,
    itemDiaPopupRoute
} from './';

const ENTITY_STATES = [...itemDiaRoute, ...itemDiaPopupRoute];

@NgModule({
    imports: [AdminhouseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ItemDiaComponent,
        ItemDiaDetailComponent,
        ItemDiaUpdateComponent,
        ItemDiaDeleteDialogComponent,
        ItemDiaDeletePopupComponent
    ],
    entryComponents: [ItemDiaComponent, ItemDiaUpdateComponent, ItemDiaDeleteDialogComponent, ItemDiaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminhouseItemDiaModule {}

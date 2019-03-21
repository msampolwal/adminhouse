import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminhouseSharedModule } from 'app/shared';
import {
    DiaComponent,
    DiaDetailComponent,
    DiaUpdateComponent,
    DiaDeletePopupComponent,
    DiaDeleteDialogComponent,
    diaRoute,
    diaPopupRoute
} from './';

const ENTITY_STATES = [...diaRoute, ...diaPopupRoute];

@NgModule({
    imports: [AdminhouseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [DiaComponent, DiaDetailComponent, DiaUpdateComponent, DiaDeleteDialogComponent, DiaDeletePopupComponent],
    entryComponents: [DiaComponent, DiaUpdateComponent, DiaDeleteDialogComponent, DiaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminhouseDiaModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminhouseSharedModule } from 'app/shared';
import {
    ComidaComponent,
    ComidaDetailComponent,
    ComidaUpdateComponent,
    ComidaDeletePopupComponent,
    ComidaDeleteDialogComponent,
    comidaRoute,
    comidaPopupRoute
} from './';

const ENTITY_STATES = [...comidaRoute, ...comidaPopupRoute];

@NgModule({
    imports: [AdminhouseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ComidaComponent, ComidaDetailComponent, ComidaUpdateComponent, ComidaDeleteDialogComponent, ComidaDeletePopupComponent],
    entryComponents: [ComidaComponent, ComidaUpdateComponent, ComidaDeleteDialogComponent, ComidaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminhouseComidaModule {}

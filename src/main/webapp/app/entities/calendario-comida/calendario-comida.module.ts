import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminhouseSharedModule } from 'app/shared';
import {
    CalendarioComidaComponent,
    CalendarioComidaDetailComponent,
    CalendarioComidaUpdateComponent,
    CalendarioComidaDeletePopupComponent,
    CalendarioComidaDeleteDialogComponent,
    calendarioComidaRoute,
    calendarioComidaPopupRoute
} from './';

const ENTITY_STATES = [...calendarioComidaRoute, ...calendarioComidaPopupRoute];

@NgModule({
    imports: [AdminhouseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CalendarioComidaComponent,
        CalendarioComidaDetailComponent,
        CalendarioComidaUpdateComponent,
        CalendarioComidaDeleteDialogComponent,
        CalendarioComidaDeletePopupComponent
    ],
    entryComponents: [
        CalendarioComidaComponent,
        CalendarioComidaUpdateComponent,
        CalendarioComidaDeleteDialogComponent,
        CalendarioComidaDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminhouseCalendarioComidaModule {}

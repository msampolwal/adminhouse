import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminhouseSharedModule } from 'app/shared';
import { AdminhouseAdminModule } from 'app/admin/admin.module';
import {
    GrupoComponent,
    GrupoDetailComponent,
    GrupoUpdateComponent,
    GrupoDeletePopupComponent,
    GrupoDeleteDialogComponent,
    grupoRoute,
    grupoPopupRoute
} from './';

const ENTITY_STATES = [...grupoRoute, ...grupoPopupRoute];

@NgModule({
    imports: [AdminhouseSharedModule, AdminhouseAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [GrupoComponent, GrupoDetailComponent, GrupoUpdateComponent, GrupoDeleteDialogComponent, GrupoDeletePopupComponent],
    entryComponents: [GrupoComponent, GrupoUpdateComponent, GrupoDeleteDialogComponent, GrupoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminhouseGrupoModule {}

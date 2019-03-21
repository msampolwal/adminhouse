import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminhouseSharedModule } from 'app/shared';
import {
    IngredienteComponent,
    IngredienteDetailComponent,
    IngredienteUpdateComponent,
    IngredienteDeletePopupComponent,
    IngredienteDeleteDialogComponent,
    ingredienteRoute,
    ingredientePopupRoute
} from './';

const ENTITY_STATES = [...ingredienteRoute, ...ingredientePopupRoute];

@NgModule({
    imports: [AdminhouseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IngredienteComponent,
        IngredienteDetailComponent,
        IngredienteUpdateComponent,
        IngredienteDeleteDialogComponent,
        IngredienteDeletePopupComponent
    ],
    entryComponents: [IngredienteComponent, IngredienteUpdateComponent, IngredienteDeleteDialogComponent, IngredienteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminhouseIngredienteModule {}

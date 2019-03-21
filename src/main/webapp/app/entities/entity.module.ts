import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AdminhouseGrupoModule } from './grupo/grupo.module';
import { AdminhouseDiaModule } from './dia/dia.module';
import { AdminhouseItemDiaModule } from './item-dia/item-dia.module';
import { AdminhouseComidaModule } from './comida/comida.module';
import { AdminhouseIngredienteModule } from './ingrediente/ingrediente.module';
import { AdminhouseProductoModule } from './producto/producto.module';
import { AdminhouseCalendarioComidaModule } from './calendario-comida/calendario-comida.module';
import { AdminhousePrecioProductoModule } from './precio-producto/precio-producto.module';
import { AdminhouseUnidadMedidaModule } from './unidad-medida/unidad-medida.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AdminhouseGrupoModule,
        AdminhouseDiaModule,
        AdminhouseItemDiaModule,
        AdminhouseComidaModule,
        AdminhouseIngredienteModule,
        AdminhouseProductoModule,
        AdminhouseCalendarioComidaModule,
        AdminhousePrecioProductoModule,
        AdminhouseUnidadMedidaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminhouseEntityModule {}

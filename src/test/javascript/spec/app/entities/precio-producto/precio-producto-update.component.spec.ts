/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AdminhouseTestModule } from '../../../test.module';
import { PrecioProductoUpdateComponent } from 'app/entities/precio-producto/precio-producto-update.component';
import { PrecioProductoService } from 'app/entities/precio-producto/precio-producto.service';
import { PrecioProducto } from 'app/shared/model/precio-producto.model';

describe('Component Tests', () => {
    describe('PrecioProducto Management Update Component', () => {
        let comp: PrecioProductoUpdateComponent;
        let fixture: ComponentFixture<PrecioProductoUpdateComponent>;
        let service: PrecioProductoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdminhouseTestModule],
                declarations: [PrecioProductoUpdateComponent]
            })
                .overrideTemplate(PrecioProductoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PrecioProductoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrecioProductoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PrecioProducto(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.precioProducto = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PrecioProducto();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.precioProducto = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

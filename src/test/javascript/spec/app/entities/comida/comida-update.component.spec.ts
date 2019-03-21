/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AdminhouseTestModule } from '../../../test.module';
import { ComidaUpdateComponent } from 'app/entities/comida/comida-update.component';
import { ComidaService } from 'app/entities/comida/comida.service';
import { Comida } from 'app/shared/model/comida.model';

describe('Component Tests', () => {
    describe('Comida Management Update Component', () => {
        let comp: ComidaUpdateComponent;
        let fixture: ComponentFixture<ComidaUpdateComponent>;
        let service: ComidaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdminhouseTestModule],
                declarations: [ComidaUpdateComponent]
            })
                .overrideTemplate(ComidaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ComidaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ComidaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Comida(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.comida = entity;
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
                    const entity = new Comida();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.comida = entity;
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

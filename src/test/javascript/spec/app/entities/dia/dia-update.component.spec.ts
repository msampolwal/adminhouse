/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AdminhouseTestModule } from '../../../test.module';
import { DiaUpdateComponent } from 'app/entities/dia/dia-update.component';
import { DiaService } from 'app/entities/dia/dia.service';
import { Dia } from 'app/shared/model/dia.model';

describe('Component Tests', () => {
    describe('Dia Management Update Component', () => {
        let comp: DiaUpdateComponent;
        let fixture: ComponentFixture<DiaUpdateComponent>;
        let service: DiaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdminhouseTestModule],
                declarations: [DiaUpdateComponent]
            })
                .overrideTemplate(DiaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DiaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Dia(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dia = entity;
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
                    const entity = new Dia();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dia = entity;
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

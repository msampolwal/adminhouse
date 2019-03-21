/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AdminhouseTestModule } from '../../../test.module';
import { CalendarioComidaUpdateComponent } from 'app/entities/calendario-comida/calendario-comida-update.component';
import { CalendarioComidaService } from 'app/entities/calendario-comida/calendario-comida.service';
import { CalendarioComida } from 'app/shared/model/calendario-comida.model';

describe('Component Tests', () => {
    describe('CalendarioComida Management Update Component', () => {
        let comp: CalendarioComidaUpdateComponent;
        let fixture: ComponentFixture<CalendarioComidaUpdateComponent>;
        let service: CalendarioComidaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdminhouseTestModule],
                declarations: [CalendarioComidaUpdateComponent]
            })
                .overrideTemplate(CalendarioComidaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CalendarioComidaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CalendarioComidaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CalendarioComida(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.calendarioComida = entity;
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
                    const entity = new CalendarioComida();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.calendarioComida = entity;
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

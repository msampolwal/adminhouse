/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdminhouseTestModule } from '../../../test.module';
import { CalendarioComidaDeleteDialogComponent } from 'app/entities/calendario-comida/calendario-comida-delete-dialog.component';
import { CalendarioComidaService } from 'app/entities/calendario-comida/calendario-comida.service';

describe('Component Tests', () => {
    describe('CalendarioComida Management Delete Component', () => {
        let comp: CalendarioComidaDeleteDialogComponent;
        let fixture: ComponentFixture<CalendarioComidaDeleteDialogComponent>;
        let service: CalendarioComidaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdminhouseTestModule],
                declarations: [CalendarioComidaDeleteDialogComponent]
            })
                .overrideTemplate(CalendarioComidaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CalendarioComidaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CalendarioComidaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});

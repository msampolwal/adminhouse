/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AdminhouseTestModule } from '../../../test.module';
import { ComidaDeleteDialogComponent } from 'app/entities/comida/comida-delete-dialog.component';
import { ComidaService } from 'app/entities/comida/comida.service';

describe('Component Tests', () => {
    describe('Comida Management Delete Component', () => {
        let comp: ComidaDeleteDialogComponent;
        let fixture: ComponentFixture<ComidaDeleteDialogComponent>;
        let service: ComidaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdminhouseTestModule],
                declarations: [ComidaDeleteDialogComponent]
            })
                .overrideTemplate(ComidaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ComidaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ComidaService);
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

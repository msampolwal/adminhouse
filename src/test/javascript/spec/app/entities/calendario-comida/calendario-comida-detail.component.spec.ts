/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminhouseTestModule } from '../../../test.module';
import { CalendarioComidaDetailComponent } from 'app/entities/calendario-comida/calendario-comida-detail.component';
import { CalendarioComida } from 'app/shared/model/calendario-comida.model';

describe('Component Tests', () => {
    describe('CalendarioComida Management Detail Component', () => {
        let comp: CalendarioComidaDetailComponent;
        let fixture: ComponentFixture<CalendarioComidaDetailComponent>;
        const route = ({ data: of({ calendarioComida: new CalendarioComida(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdminhouseTestModule],
                declarations: [CalendarioComidaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CalendarioComidaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CalendarioComidaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.calendarioComida).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminhouseTestModule } from '../../../test.module';
import { DiaDetailComponent } from 'app/entities/dia/dia-detail.component';
import { Dia } from 'app/shared/model/dia.model';

describe('Component Tests', () => {
    describe('Dia Management Detail Component', () => {
        let comp: DiaDetailComponent;
        let fixture: ComponentFixture<DiaDetailComponent>;
        const route = ({ data: of({ dia: new Dia(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdminhouseTestModule],
                declarations: [DiaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DiaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DiaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dia).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

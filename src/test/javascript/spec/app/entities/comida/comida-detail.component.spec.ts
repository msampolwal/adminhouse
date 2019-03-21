/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminhouseTestModule } from '../../../test.module';
import { ComidaDetailComponent } from 'app/entities/comida/comida-detail.component';
import { Comida } from 'app/shared/model/comida.model';

describe('Component Tests', () => {
    describe('Comida Management Detail Component', () => {
        let comp: ComidaDetailComponent;
        let fixture: ComponentFixture<ComidaDetailComponent>;
        const route = ({ data: of({ comida: new Comida(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdminhouseTestModule],
                declarations: [ComidaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ComidaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ComidaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.comida).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

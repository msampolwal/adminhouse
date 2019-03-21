/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminhouseTestModule } from '../../../test.module';
import { PrecioProductoDetailComponent } from 'app/entities/precio-producto/precio-producto-detail.component';
import { PrecioProducto } from 'app/shared/model/precio-producto.model';

describe('Component Tests', () => {
    describe('PrecioProducto Management Detail Component', () => {
        let comp: PrecioProductoDetailComponent;
        let fixture: ComponentFixture<PrecioProductoDetailComponent>;
        const route = ({ data: of({ precioProducto: new PrecioProducto(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdminhouseTestModule],
                declarations: [PrecioProductoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PrecioProductoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PrecioProductoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.precioProducto).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AdminhouseTestModule } from '../../../test.module';
import { ItemDiaDetailComponent } from 'app/entities/item-dia/item-dia-detail.component';
import { ItemDia } from 'app/shared/model/item-dia.model';

describe('Component Tests', () => {
    describe('ItemDia Management Detail Component', () => {
        let comp: ItemDiaDetailComponent;
        let fixture: ComponentFixture<ItemDiaDetailComponent>;
        const route = ({ data: of({ itemDia: new ItemDia(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdminhouseTestModule],
                declarations: [ItemDiaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ItemDiaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemDiaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.itemDia).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AdminhouseTestModule } from '../../../test.module';
import { ItemDiaUpdateComponent } from 'app/entities/item-dia/item-dia-update.component';
import { ItemDiaService } from 'app/entities/item-dia/item-dia.service';
import { ItemDia } from 'app/shared/model/item-dia.model';

describe('Component Tests', () => {
    describe('ItemDia Management Update Component', () => {
        let comp: ItemDiaUpdateComponent;
        let fixture: ComponentFixture<ItemDiaUpdateComponent>;
        let service: ItemDiaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AdminhouseTestModule],
                declarations: [ItemDiaUpdateComponent]
            })
                .overrideTemplate(ItemDiaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ItemDiaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemDiaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ItemDia(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.itemDia = entity;
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
                    const entity = new ItemDia();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.itemDia = entity;
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

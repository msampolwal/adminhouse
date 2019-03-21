import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemDia } from 'app/shared/model/item-dia.model';

@Component({
    selector: 'jhi-item-dia-detail',
    templateUrl: './item-dia-detail.component.html'
})
export class ItemDiaDetailComponent implements OnInit {
    itemDia: IItemDia;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemDia }) => {
            this.itemDia = itemDia;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDia } from 'app/shared/model/dia.model';

@Component({
    selector: 'jhi-dia-detail',
    templateUrl: './dia-detail.component.html'
})
export class DiaDetailComponent implements OnInit {
    dia: IDia;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dia }) => {
            this.dia = dia;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComida } from 'app/shared/model/comida.model';

@Component({
    selector: 'jhi-comida-detail',
    templateUrl: './comida-detail.component.html'
})
export class ComidaDetailComponent implements OnInit {
    comida: IComida;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ comida }) => {
            this.comida = comida;
        });
    }

    previousState() {
        window.history.back();
    }
}

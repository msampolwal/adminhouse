import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICalendarioComida } from 'app/shared/model/calendario-comida.model';

@Component({
    selector: 'jhi-calendario-comida-detail',
    templateUrl: './calendario-comida-detail.component.html'
})
export class CalendarioComidaDetailComponent implements OnInit {
    calendarioComida: ICalendarioComida;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ calendarioComida }) => {
            this.calendarioComida = calendarioComida;
        });
    }

    previousState() {
        window.history.back();
    }
}

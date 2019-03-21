import { Moment } from 'moment';

export interface ICalendarioComida {
    id?: number;
    fecha?: Moment;
    grupoId?: number;
    diaId?: number;
    detalleDia?: string;
    comidasDetalle?: string[];
}

export class CalendarioComida implements ICalendarioComida {
    constructor(
        public id?: number,
        public fecha?: Moment,
        public grupoId?: number,
        public diaId?: number,
        public detalleDia?: string,
        public comidasDetalle?: string[]
    ) {}
}

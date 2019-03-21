import { ICalendarioComida } from 'app/shared/model//calendario-comida.model';
import { IDia } from 'app/shared/model//dia.model';
import { IUser } from 'app/core/user/user.model';

export interface IGrupo {
    id?: number;
    calendarioComidas?: ICalendarioComida[];
    dias?: IDia[];
    users?: IUser[];
}

export class Grupo implements IGrupo {
    constructor(public id?: number, public calendarioComidas?: ICalendarioComida[], public dias?: IDia[], public users?: IUser[]) {}
}

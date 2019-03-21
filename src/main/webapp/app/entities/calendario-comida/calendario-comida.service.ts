import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICalendarioComida } from 'app/shared/model/calendario-comida.model';

type EntityResponseType = HttpResponse<ICalendarioComida>;
type EntityArrayResponseType = HttpResponse<ICalendarioComida[]>;

@Injectable({ providedIn: 'root' })
export class CalendarioComidaService {
    private resourceUrl = SERVER_API_URL + 'api/calendario-comidas';

    constructor(private http: HttpClient) {}

    create(calendarioComida: ICalendarioComida): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(calendarioComida);
        return this.http
            .post<ICalendarioComida>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(calendarioComida: ICalendarioComida): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(calendarioComida);
        return this.http
            .put<ICalendarioComida>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICalendarioComida>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICalendarioComida[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(calendarioComida: ICalendarioComida): ICalendarioComida {
        const copy: ICalendarioComida = Object.assign({}, calendarioComida, {
            fecha: calendarioComida.fecha != null && calendarioComida.fecha.isValid() ? calendarioComida.fecha.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((calendarioComida: ICalendarioComida) => {
            calendarioComida.fecha = calendarioComida.fecha != null ? moment(calendarioComida.fecha) : null;
        });
        return res;
    }
}

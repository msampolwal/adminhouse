import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDia } from 'app/shared/model/dia.model';

type EntityResponseType = HttpResponse<IDia>;
type EntityArrayResponseType = HttpResponse<IDia[]>;

@Injectable({ providedIn: 'root' })
export class DiaService {
    private resourceUrl = SERVER_API_URL + 'api/dias';

    constructor(private http: HttpClient) {}

    create(dia: IDia): Observable<EntityResponseType> {
        return this.http.post<IDia>(this.resourceUrl, dia, { observe: 'response' });
    }

    update(dia: IDia): Observable<EntityResponseType> {
        return this.http.put<IDia>(this.resourceUrl, dia, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDia[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

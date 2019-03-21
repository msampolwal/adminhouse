import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IComida } from 'app/shared/model/comida.model';

type EntityResponseType = HttpResponse<IComida>;
type EntityArrayResponseType = HttpResponse<IComida[]>;

@Injectable({ providedIn: 'root' })
export class ComidaService {
    private resourceUrl = SERVER_API_URL + 'api/comidas';

    constructor(private http: HttpClient) {}

    create(comida: IComida): Observable<EntityResponseType> {
        return this.http.post<IComida>(this.resourceUrl, comida, { observe: 'response' });
    }

    update(comida: IComida): Observable<EntityResponseType> {
        return this.http.put<IComida>(this.resourceUrl, comida, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IComida>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IComida[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

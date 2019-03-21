import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IItemDia } from 'app/shared/model/item-dia.model';

type EntityResponseType = HttpResponse<IItemDia>;
type EntityArrayResponseType = HttpResponse<IItemDia[]>;

@Injectable({ providedIn: 'root' })
export class ItemDiaService {
    private resourceUrl = SERVER_API_URL + 'api/item-dias';

    constructor(private http: HttpClient) {}

    create(itemDia: IItemDia): Observable<EntityResponseType> {
        return this.http.post<IItemDia>(this.resourceUrl, itemDia, { observe: 'response' });
    }

    update(itemDia: IItemDia): Observable<EntityResponseType> {
        return this.http.put<IItemDia>(this.resourceUrl, itemDia, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IItemDia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IItemDia[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

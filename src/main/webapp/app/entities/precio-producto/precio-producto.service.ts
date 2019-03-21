import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPrecioProducto } from 'app/shared/model/precio-producto.model';

type EntityResponseType = HttpResponse<IPrecioProducto>;
type EntityArrayResponseType = HttpResponse<IPrecioProducto[]>;

@Injectable({ providedIn: 'root' })
export class PrecioProductoService {
    private resourceUrl = SERVER_API_URL + 'api/precio-productos';

    constructor(private http: HttpClient) {}

    create(precioProducto: IPrecioProducto): Observable<EntityResponseType> {
        return this.http.post<IPrecioProducto>(this.resourceUrl, precioProducto, { observe: 'response' });
    }

    update(precioProducto: IPrecioProducto): Observable<EntityResponseType> {
        return this.http.put<IPrecioProducto>(this.resourceUrl, precioProducto, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPrecioProducto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPrecioProducto[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

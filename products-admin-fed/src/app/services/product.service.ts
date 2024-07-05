import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Product } from '../models/product.model';
import { Paging } from '../models/paging.model';

const baseUrl = 'http://localhost:8080/productsadmin';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Paging> {
    return this.http.get<Paging>(`${baseUrl}/products`);
  }

  get(id: any): Observable<Product> {
    return this.http.get<Product>(`${baseUrl}/product/${id}`);
  }

  create(data: any): Observable<any> {
    return this.http.post(`${baseUrl}/product/save`, data);
  }

  update(data: any): Observable<any> {
    return this.http.put(`${baseUrl}/product/update`, data);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${baseUrl}/product/${id}`);
  }
  
  findByName(name: any, page: number, sortBy: string, sortOrder: string): Observable<Paging> {
    return this.http.get<Paging>(`${baseUrl}/products?name=${name}&page=${page}&size=5&sortBy=${sortBy}&sortOrder=${sortOrder}`);
  }

}

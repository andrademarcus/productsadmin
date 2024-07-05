import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Login } from '../models/login.model';

const baseUrl = 'http://localhost:8080/productsadmin';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private http: HttpClient) {}

  login(data: Login): Observable<any> {
    return this.http.post(`${baseUrl}/auth/signin`, data);
  }

}
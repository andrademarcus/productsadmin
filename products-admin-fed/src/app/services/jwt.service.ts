import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class JwtService {
  constructor(public jwtHelper: JwtHelperService) {}

  public getToken(): string {
    return localStorage.getItem('token') || '';
  }

  public setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  public isAuthenticated(): boolean {
    const token = this.getToken();
    return !this.jwtHelper.isTokenExpired(token);
  }

  public isAdmin(): boolean {
    const token = this.getToken();
    return this.isAuthenticated() && this.jwtHelper.decodeToken(token).roles.includes('ROLE_ADMIN');
  }

}
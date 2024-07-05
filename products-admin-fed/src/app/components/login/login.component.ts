import { Component } from '@angular/core';
import { Login } from '../../models/login.model';
import { LoginService } from '../../services/login.service';
import { JwtService } from '../../services/jwt.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  login: Login = {
    username: '',
    password: ''
  };
  submitted = false;

  constructor(private loginService: LoginService, private jwtService: JwtService, private router: Router) {}

  requestLogin(): void {
    const data = {
      username: this.login.username,
      password: this.login.password
    };

    this.loginService.login(data).subscribe({
      next: (res) => {
        this.jwtService.setToken(res.accessToken);
        console.log('Logged in successfully');
        this.router.navigate(['products']);
      },
      error: (e) => console.error(e)
    });
  }

}

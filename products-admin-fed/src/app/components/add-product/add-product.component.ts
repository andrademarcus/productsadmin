import { Component, OnInit } from '@angular/core';
import { Product } from '../../models/product.model';
import { ProductService } from '../../services/product.service';
import { JwtService } from '../../services/jwt.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css'],
})
export class AddProductComponent implements OnInit {

  form: FormGroup = this.formBuilder.group({
    name: ['', [Validators.required]],
    description: [null, Validators.required],
    price: [null, Validators.required],
    categoryPath: [null, Validators.required],
    available: ['true']
  });

  submitted = false;

  constructor(private productService: ProductService, private jwtService: JwtService, private router: Router, private formBuilder: FormBuilder) {}
  
  ngOnInit(): void {
    if (!this.jwtService.isAuthenticated()) {
      this.router.navigate(['login']);
    } else if (this.jwtService.isAuthenticated() && !this.jwtService.isAdmin()) {
      this.router.navigate(['access-denied']);
    }
  }

  saveProduct(): void {

    if (this.form.valid) {
      this.productService.create(this.form.value)
      .subscribe({
        next: (res) => {
          this.router.navigate(['products']);
        },
        error: (e) => console.error(e)
      });
    }

  }

  cancel(): void {
    this.router.navigate(['/products']);
  }

  numberOnly(event: any): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;

  }

}

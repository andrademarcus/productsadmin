import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../../services/product.service';
import { JwtService } from '../../services/jwt.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrl: './edit-product.component.css'
})
export class EditProductComponent {

  form: FormGroup = this.formBuilder.group({
    id: [null],
    name: ['', [Validators.required]],
    description: [null, Validators.required],
    price: [null, Validators.required],
    categoryPath: [null, Validators.required],
    available: ['true']
  });

  submitted = false;

  constructor(
    private productService: ProductService, 
    private jwtService: JwtService, 
    private router: Router, 
    private route: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {}
  
  ngOnInit(): void {
    if (!this.jwtService.isAuthenticated()) {
      this.router.navigate(['login']);
    } else if (this.jwtService.isAuthenticated() && !this.jwtService.isAdmin()) {
      this.router.navigate(['access-denied']);
    } else {
      this.getProduct(this.route.snapshot.params['id']);
    }

  }

  updateProduct(): void {
    if (this.form.valid) {
      this.productService.update(this.form.value).subscribe({
        next: (res) => {
          this.router.navigate(['/products']);
        },
        error: (e) => console.error(e)
      });
    }
  }

  getProduct(id: string): void {
    this.productService.get(id).subscribe({
      next: (data) => {
        this.form.setValue({
          id: data.id,
          name: data.name,
          description: data.description,
          price: data.price,
          categoryPath: data.categoryPath,
          available: data.available?.toString()
        });
      },
      error: (e) => console.error(e)
    });
  }

  deleteProduct(): void {
    this.productService.delete(this.form.value.id)
    .subscribe((data) => {
      this.router.navigate(['/products']);
    });
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

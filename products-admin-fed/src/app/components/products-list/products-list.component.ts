import { Component, OnInit } from '@angular/core';
import { Product } from '../../models/product.model';
import { ProductService } from '../../services/product.service';
import { JwtService } from '../../services/jwt.service';
import { Router } from '@angular/router';
import { Paging } from '../../models/paging.model';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.css'],
})
export class ProductsListComponent implements OnInit {
  
  currentPage = 0;
  paging?: Paging;
  sortBy: string = 'id';
  sortOrder: string = 'desc';
  products?: Product[];
  currentIndex = -1;
  name = '';

  constructor(private productService: ProductService, private jwtService: JwtService, private router: Router) {}

  ngOnInit(): void {

    if (!this.jwtService.isAuthenticated()) {
      this.router.navigate(['login']);
    } else if (this.jwtService.isAuthenticated() && !this.jwtService.isAdmin()) {
      this.router.navigate(['access-denied']);
    } else {
      this.retrieveProducts();
    }

  }

  retrieveProducts(): void {
    this.searchName();
  }

  refreshList(): void {
    this.retrieveProducts();
    this.currentIndex = -1;
  }

  editProduct(productId: number): void {
    this.router.navigate(['product/edit/', productId]);
  }


  deleteProduct(productId: number): void {
    this.router.navigate(['edit-product', productId]);
  }

  setActiveProduct(product: Product, index: number): void {
    this.currentIndex = index;
  }

  nextPage(): void {
    this.currentPage++;
    this.searchName();
  }

  previousPage(): void {
    this.currentPage--;
    this.searchName();
  }

  sort(sortBy?: any): void {
    this.sortBy = sortBy;
    this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
    this.searchName();
  }

  searchName(): void {
    this.currentIndex = -1;

    this.productService.findByName(this.name, this.currentPage, this.sortBy, this.sortOrder).subscribe({
      next: (data) => {
        this.paging = data;
        this.products = data.content;
      },
      error: (e) => console.error(e)
    });
  }
}

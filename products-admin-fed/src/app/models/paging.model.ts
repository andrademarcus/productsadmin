import { Product } from "./product.model";

export class Paging {
    totalPages?: number;
    totalElements?: number;
    currentPage?: number;
    pageSize?: number;
    first?: boolean;
    last?: boolean;
    content?: Product[];
}
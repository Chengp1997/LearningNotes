import { BookItem } from "@/types";

export class ShoppingCartItem {
  readonly book: BookItem;
  quantity: number;

  constructor(theBook: BookItem) {
    this.book = theBook;
    this.quantity = 1;
  }
}

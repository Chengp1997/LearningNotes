import { defineStore } from "pinia";
import { ShoppingCart } from "@/models/ShoppingCart";
import { BookItem, CustomerForm, OrderDetails } from "@/types";
import { apiUrl } from "@/services/ApiService";
import { useOrderDetailsStore } from "@/stores/OrderDetailsStore";

const CART_STORAGE_KEY = "ShoppingCart";

export const useCartStore = defineStore("CartStore", {
  state: () => {
    const initCart = new ShoppingCart();
    const cartString = localStorage.getItem(CART_STORAGE_KEY);
    if (cartString !== null) {
      // cartString is a string
      const cartFromStorage = JSON.parse(cartString) as ShoppingCart;
      Object.assign(initCart, cartFromStorage);
    }
    return {
      cart: initCart,
    };
  },
  getters: {
    count(): number {
      console.log(this.cart.numberOfItems);
      return this.cart.numberOfItems;
    },
    subtotal(): number {
      return this.cart.subtotal;
    },
    total(): number {
      return this.cart.total;
    },
    surcharge(): number {
      return this.cart.surcharge;
    },
  },

  actions: {
    clearCart() {
      this.cart.clear();
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.cart));
    },
    addToCart(book: BookItem) {
      this.cart.addBook(book);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.cart));
    },
    updateBookQuantity(book: BookItem, quantity: number) {
      this.cart.update(book, quantity);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.cart));
    },
    async placeOrder(customerForm: CustomerForm) {
      const order = { cart: this.cart, customerForm: customerForm };
      const orderDetailStore = useOrderDetailsStore();
      orderDetailStore.clearOrderDetails();
      // console.log(JSON.stringify(order));

      const url = apiUrl + "orders";
      const orderDetails: OrderDetails = await fetch(url, {
        mode: "cors",
        cache: "no-cache",
        credentials: "same-origin",
        headers: {
          "Content-Type": "application/json",
        },
        redirect: "follow",
        referrer: "client",
        method: "POST", // or 'PUT'
        body: JSON.stringify(order),
      }).then((response) => response.json());
      orderDetailStore.setOrderDetails(orderDetails);
      this.clearCart();
    },
  },
});

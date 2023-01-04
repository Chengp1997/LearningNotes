<script setup lang="ts">
import { useCartStore } from "@/stores/CartStore";
import { useCategoryStore } from "@/stores/CategoryStore";

const cartStore = useCartStore();
import { BookItem } from "@/types";
const categoryStore = useCategoryStore();
const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase();
  name = name.replace(/ /g, "-");
  name = name.replace(/'/g, "");
  return `${name}.gif`;
};

const updateCart = function (book: BookItem, quantity: number) {
  cartStore.updateBookQuantity(book, quantity);
};
const clearCart = function () {
  cartStore.clearCart();
};
const removeBook = function (book: BookItem) {
  cartStore.updateBookQuantity(book, 0);
};
</script>

<style scoped>
.cart-table {
  display: grid;
  grid-template-columns: max-content minmax(10em, 20em) repeat(3, max-content);
  row-gap: 1em;
  width: fit-content;
  margin: 0 auto;
  background-color: aliceblue;
}

ul {
  display: contents;
}

ul > li {
  display: contents;
}

.table-heading {
  background-color: #333;
  color: white;
}

.table-heading > * {
  background-color: #333;
  padding: 0.5em;
}

.heading-book {
  grid-column: 1 / 3;
}

.heading-price {
  grid-column: 3 / 4;
  text-align: center;
}

.heading-quantity {
  grid-column: 4 / 5;
  text-align: center;
}

.heading-subtotal {
  text-align: center;
  grid-column: -2 / -1;
}

.cart-book-image {
  padding: 0 1em;
}

.cart-book-image > * {
  margin-left: auto;
  margin-right: 0;
}

img {
  display: block;
  width: 100px;
  height: auto;
}

.cart-book-price {
  padding-left: 1em;
  text-align: right;
}

.cart-book-quantity {
  padding-left: 1em;
  padding-right: 1em;
}

.cart-book-subtotal {
  text-align: right;
  padding-left: 1em;
  padding-right: 1em;
}

.cart-remove-button {
  padding-top: 10px;
  padding-left: 1em;
  padding-right: 1em;
  text-decoration: underline;
  cursor: pointer;
  color: #cccccc;
}

.cart-remove-button:hover {
  color: black;
}

/* Row separators in the table */

.line-sep {
  display: block;
  height: 2px;
  background-color: gray;
  grid-column: 1 / -1;
}

/* Increment/decrement buttons */

/* TODO Consider using icon buttons for your increment and decrement buttons. */
/* TODO Modify the CSS here to suit your own design. */
/* TODO In particular, you may or may not have custom properties */
/* TODO primary-color and primary-color-dark defined in your global CSS file. */
.icon-button {
  border: none;
  cursor: pointer;
  text-decoration: none;
  border-radius: 16px;
}

.inc-button {
  font-size: 1rem;
  color: white;
  background: var(--primary-button-color);
  margin-right: 0.25em;
}

.inc-button:hover {
  color: var(--primary-button-color-hover);
}

.dec-button {
  font-size: 1rem;
  color: #ccc;
}

.dec-button:hover {
  color: #aaa;
}

select {
  background-color: var(--primary-background-color);
  color: white;
  border: 2px solid var(--secondary-background-color);
  border-radius: 3px;
}

.link {
  color: var(--button-text-color);
  text-decoration: none;
}

.buttons {
  margin: 25px auto;
  width: 600px;
}

.clear-button {
  justify-content: flex-end;
  margin-left: auto;
  background-color: lightgray;
}

.message {
  margin: 10px auto;
  width: 700px;
  text-align: right;
}

.price-text {
  font-weight: bold;
  text-decoration-line: underline;
}

.empty-text {
  font-size: 20px;
}
.quantity {
  font-size: 20px;
}
</style>

<template>
  <div class="message">
    <span v-if="cartStore.count > 1" class="center">
      Your shopping cart contains {{ cartStore.count }} items
    </span>
    <span v-if="cartStore.count == 1" class="center">
      Your shopping cart contains {{ cartStore.count }} item
    </span>
    <span v-if="cartStore.count == 0" class="empty-text center"
      >Your shopping cart is Empty
    </span>
  </div>
  <div class="buttons center">
    <button class="button secondary_button">
      <router-link
        :to="{
          name: 'category-view',
          params: { name: categoryStore.lastSelected },
        }"
        class="link"
      >
        <span>Continue Shopping</span>
      </router-link>
    </button>
  </div>
  <div class="buttons">
    <button
      class="button tertiary_button clear-button"
      @click="clearCart"
      v-if="cartStore.count != 0"
    >
      <span>Clear</span>
    </button>
  </div>
  <div class="cart-table" v-if="cartStore.count > 0">
    <ul>
      <li class="table-heading">
        <div class="heading-book">Book</div>
        <div class="heading-price">Price</div>
        <div class="heading-quantity">Quantity</div>
        <div class="heading-subtotal">Amount</div>
      </li>
      <template v-for="item in cartStore.cart.items" :key="item.book.bookId">
        <li>
          <div class="cart-book-image">
            <img
              :src="
                require('@/assets/images/books/' + bookImageFileName(item.book))
              "
              :alt="item.book.title"
              width="100px"
              height="auto"
            />
          </div>
          <div class="cart-book-title">{{ item.book.title }}</div>
          <div class="cart-book-price">
            {{ $filters.priceFormat(item.book.price) }}
          </div>
          <div class="cart-book-quantity">
            <button
              class="icon-button dec-button"
              @click="updateCart(item.book, item.quantity - 1)"
            >
              -
            </button>
            <span class="quantity">{{ item.quantity }}</span>
            <button
              class="icon-button inc-button"
              @click="updateCart(item.book, item.quantity + 1)"
            >
              +
            </button>
          </div>
          <div>
            <div class="cart-book-subtotal">
              {{ $filters.priceFormat(item.quantity * item.book.price) }}
            </div>
            <div class="cart-remove-button" @click="removeBook(item.book)">
              remove
            </div>
          </div>
        </li>
        <li class="line-sep"></li>
      </template>
    </ul>
  </div>
  <div class="message" v-if="cartStore.count > 0">
    <span class="price-text"
      >Subtotal: {{ $filters.priceFormat(cartStore.subtotal) }}</span
    >
    <br />
    <span class="price-text"
      >Total: {{ $filters.priceFormat(cartStore.total) }}</span
    >
  </div>
  <div class="buttons center" v-if="cartStore.count > 0">
    <button class="button primary_button">
      <router-link :to="'/checkout'" class="link">
        <span>Proceed to Checkout</span>
      </router-link>
    </button>
  </div>
</template>

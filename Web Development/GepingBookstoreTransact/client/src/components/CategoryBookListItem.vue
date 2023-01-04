<script setup lang="ts">
import { defineProps } from "vue";
import { BookItem } from "@/types";
import { useCartStore } from "@/stores/CartStore";
const cartStore = useCartStore();
const props = defineProps<{
  book: BookItem;
}>();
const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase();
  name = name.replace(/ /g, "-");
  name = name.replace(/'/g, "");
  return `${name}.gif`;
};
const addCart = function (book: BookItem) {
  cartStore.addToCart(book);
};
</script>
<style scoped>
.book-box {
  display: flex;
  background: var(--secondary-background-color);
  width: 420px;
  padding: 5px;
  margin-bottom: 10px;
}

.book-detail-info {
  flex-direction: column;
  width: 200px;
}

.book-title {
  font-weight: bold;
}

.book-author {
  font-style: italic;
}

.book-image {
  width: 140px;
  height: 200px;
}
.book-detail-info div {
  padding: 5px;
}
.hide-button {
  display: none;
}
.overlap-parent {
  position: relative;
  width: 140px;
  height: 200px;
}
.overlap-child {
  position: absolute;
  left: 10%;
  bottom: 0;
}
</style>

<template>
  <div class="book-box center">
    <div class="overlap-parent">
      <img
        :src="require('@/assets/images/books/' + bookImageFileName(book))"
        :alt="book.title"
        class="book-image"
      />
      <button
        class="button tertiary_button overlap-child"
        :class="book.isPublic === true ? 'tertiary_button' : 'hide-button'"
      >
        Read Now
      </button>
    </div>
    <div class="book-detail-info center">
      <div class="book-title">{{ book.title }}</div>
      <div class="book-author">{{ book.author }}</div>
      <div class="center">
        <img src="@/assets/images/icon/score_full_star.png" />
        <img src="@/assets/images/icon/score_full_star.png" />
        <img src="@/assets/images/icon/score_full_star.png" />
        <img src="@/assets/images/icon/score_full_star.png" />
        <img src="@/assets/images/icon/score_full_star.png" />
      </div>
      <div class="book-price">{{ $filters.priceFormat(book.price) }}</div>
      <button class="button tertiary_button" @click="addCart(book)">
        Add to Cart
      </button>
    </div>
  </div>
</template>

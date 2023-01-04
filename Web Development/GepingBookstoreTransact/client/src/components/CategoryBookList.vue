<script setup lang="ts">
import CategoryBookListItem from "@/components/CategoryBookListItem.vue";
import { useBookStore } from "@/stores/BookStore";

const bookStore = useBookStore();
</script>
<style scoped>
.book-list {
  padding: 10px;
  display: flex;
  flex-flow: row wrap;
  align-content: flex-start;
  /*flex-wrap: wrap;*/
  justify-content: space-evenly;
  margin: 10px;
}
.book-item {
  box-sizing: border-box;
  flex: 0 0 45%;
  margin: 10px;
}
</style>

<template>
  <div class="book-list">
    <template v-for="book in bookStore.bookList" :key="book.bookId">
      <div class="book-item center">
        <category-book-list-item :book="book">{{
          book.name
        }}</category-book-list-item>
      </div>
    </template>
  </div>
</template>

<script setup lang="ts">
import { useCategoryStore } from "@/stores/CategoryStore";

//already fetch category list in AppFechCategories file
const categoryStore = useCategoryStore();
</script>

<style scoped>
.header-dropdown {
  position: relative;
}

.header-dropdown ul {
  background-color: white;
  color: black;
  display: none;
}

.header-dropdown li {
  padding: 0.25em 0.5em;
  transition: background 1s;
}

.header-dropdown a {
  color: black;
  text-decoration: none;
}

.header-dropdown li:hover {
  background: lightgray;
}

.header-dropdown:hover ul {
  display: block;
  position: absolute;
  min-width: 8em;
  z-index: 1;
  cursor: pointer;
}

.header-dropdown img {
  width: 18px;
  height: 18px;
  margin-right: 5px;
}
</style>

<template>
  <div class="header-dropdown">
    <button class="button primary_button">
      <img src="@/assets/images/icon/categoryArrow.png" />
      Categories
    </button>
    <ul>
      <li
        v-for="category in categoryStore.categoryList"
        :key="category.categoryId"
      >
        <router-link :to="'../category/' + category.name">
          {{ category.name }}
        </router-link>
      </li>
    </ul>
  </div>
</template>

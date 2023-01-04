<script setup lang="ts">
import { useRoute } from "vue-router";
import { useCategoryStore } from "@/stores/CategoryStore";

const route = useRoute();
const categoryStore = useCategoryStore();
</script>

<style scoped>
.category-nav {
  background: var(--secondary-background-color);
  display: flex;
  flex-direction: column;
  padding: 1em;
  gap: 1em;
  width: 150px;
  height: 400px;
  margin: 50px;
}
.link {
  color: var(--button-text-color);
  text-decoration: none;
}
.category-button-selected {
  background: var(--secondary-button-color-visited);
  color: white;
}
</style>

<template>
  <div class="center">
    <div class="category-nav center">
      <button
        v-for="category in categoryStore.categoryList"
        :key="category.categoryId"
        class="button"
        :class="
          category.name === route.params.name
            ? 'category-button-selected'
            : 'secondary_button'
        "
      >
        <router-link
          :to="{ name: 'category-view', params: { name: category.name } }"
          class="link"
        >
          {{ category.name }}
        </router-link>
      </button>
      <img src="@/assets/images/icon/vector_down.png" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { provide } from "vue";
import AppHeader from "@/components/AppHeader.vue";
import AppFooter from "@/components/AppFooter.vue";
import { useCategoryStore } from "@/stores/CategoryStore";

const categoryStore = useCategoryStore();
categoryStore.fetchCategories();

provide("categoryList", categoryStore.categoryList);
</script>

<style></style>

<template>
  <div id="app-fetch-categories">
    <app-header></app-header>
    <router-view></router-view>
    <app-footer></app-footer>
  </div>
</template>

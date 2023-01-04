<script setup lang="ts">
import HeaderDropdown from "@/components/HeaderDropdown.vue";
</script>

<style scoped>
.navigation {
  background: var(--navigation-background-color);
  border-radius: 15px;
  width: 700px;
  height: 60px;
}

.link {
  color: var(--button-text-color);
  text-decoration: none;
}
</style>

<template>
  <div class="navigation center">
    <button class="button primary_button">
      <router-link :to="'/'" class="link"> Home </router-link>
    </button>
    <button class="button primary_button">Best Seller</button>
    <button class="button primary_button">My Favorite</button>
    <header-dropdown></header-dropdown>
  </div>
</template>

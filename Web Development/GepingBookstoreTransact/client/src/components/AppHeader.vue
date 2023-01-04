<script setup lang="ts">
import HeaderBrand from "@/components/HeaderBrand.vue";
import HeaderNav from "@/components/HeaderNav.vue";
import HeaderSearchBar from "@/components/HeaderSearchBar.vue";
import HeaderSignInBar from "@/components/HeaderSignInBar.vue";
import HeaderShoppingCart from "@/components/HeaderShoppingCart.vue";
</script>

<style scoped>
header {
  background: var(--secondary-background-color);
  display: flex;
  flex-direction: row;
  padding: 1em;
  gap: 1em;
  flex-wrap: wrap;
  justify-content: center;
}

@media (max-width: 600px) {
  header {
    flex-direction: column;
  }
}
</style>

<template>
  <header class="container">
    <header-brand></header-brand>
    <header-nav></header-nav>
    <header-search-bar></header-search-bar>
    <header-sign-in-bar></header-sign-in-bar>
    <header-shopping-cart></header-shopping-cart>
  </header>
</template>

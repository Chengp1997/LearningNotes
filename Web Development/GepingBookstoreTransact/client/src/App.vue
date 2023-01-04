<script setup lang="ts">
import AppFetchCategories from "@/components/AppFetchCategories.vue";
const PriceFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
  minimumFractionDigits: 2,
});
</script>

<style></style>

<template>
  <div id="app">
    <Suspense>
      <AppFetchCategories></AppFetchCategories>
    </Suspense>
  </div>
</template>

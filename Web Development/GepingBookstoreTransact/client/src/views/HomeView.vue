<script setup lang="ts">
import HomeGallery from "@/components/HomeGallery.vue";
</script>

<style scoped>
.home-page {
  display: flex;
  padding-bottom: 50px;
  flex-wrap: wrap;
}

.link {
  color: var(--button-text-color);
  text-decoration: none;
}
</style>
<template>
  <div class="home-page">
    <home-gallery></home-gallery>
    <div class="center" style="width: 100%">
      <button class="button call_to_action_button">
        <router-link :to="'../category/Science'" class="link">
          Let's Shop!
        </router-link>
      </button>
    </div>
  </div>
</template>

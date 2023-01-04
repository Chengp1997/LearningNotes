<script setup lang="ts"></script>

<style scoped>
footer {
  background: var(--secondary-background-color);
  display: flex;
  gap: 1em;
  flex-wrap: wrap;
  justify-content: space-evenly;
  align-items: center;
}

.links,
.social-media-icons {
  gap: 0.5em;
}
</style>

<template>
  <footer class="container">
    <div id="author">2022 Bookstore By Geping Chen</div>
    <div class="links center">
      <router-link to="/">about</router-link>
      <router-link to="/">contact</router-link>
      <router-link to="/">directions</router-link>
    </div>
    <div class="social-media-icons center">
      <router-link to="/">
        <img src="@/assets/images/icon/instagram.png" />
      </router-link>
      <router-link to="/">
        <img src="@/assets/images/icon/facebook.png" />
      </router-link>
      <router-link to="/">
        <img src="@/assets/images/icon/twitter.png" />
      </router-link>
    </div>
  </footer>
</template>

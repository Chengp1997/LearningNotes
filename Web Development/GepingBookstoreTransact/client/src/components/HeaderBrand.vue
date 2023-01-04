<script setup lang="ts"></script>

<style scoped>
.bookstore_brand {
  width: 300px;
}

.text-logo {
  line-height: 50px;
  font-size: 30px;
  font-family: var(--title-font-family);
  text-decoration: none;
}

.logo {
  width: 50px;
}
</style>

<template>
  <div class="bookstore_brand center">
    <router-link to="/">
      <img
        class="logo"
        src="@/assets/images/icon/bookstore-logo.png"
        alt="logo"
      />
    </router-link>
    <router-link to="/" class="text-logo"> ShopBooks! </router-link>
  </div>
</template>

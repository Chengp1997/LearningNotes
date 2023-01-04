<script setup lang="ts">
import { useCartStore } from "@/stores/CartStore";
const cartStore = useCartStore();
</script>
<style>
.shopping-cart {
  position: relative;
  width: 50px;
  height: 50px;
  background-color: var(--secondary-background-color);
  border-radius: 5px;
  border: 0 none;
  cursor: pointer;
  transition: background-color 2s;
}
.shopping-cart:hover {
  background-color: var(--primary-button-color-visited);
}

.child {
  position: absolute;
  right: 0;
  top: 0;
  color: white;
  font-weight: bold;
  background-color: darkseagreen;
  border-radius: 8px;
  /*margin: 5px;*/
  border: 0 none;
}
.span {
  font-size: 5px;
  padding: 2px;
}
.cart-image {
  width: 50px;
  height: 40px;
}
</style>

<style scoped></style>
<template>
  <div class="center">
    <router-link class="shopping-cart center" to="/cart">
      <img src="@/assets/images/icon/shoppingCart.png" class="cart-image" />
      <div id="cartCount" class="center child">
        <span class="span">({{ cartStore.count }})</span>
      </div>
    </router-link>
  </div>
</template>

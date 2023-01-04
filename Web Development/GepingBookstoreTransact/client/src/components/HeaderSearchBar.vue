<script setup lang="ts"></script>

<style scoped>
input[type="text"] {
  margin: 0.5em 0;
  padding: 0.5em;
  height: 50px;
  width: 700px;
}

.submit-button {
  background: black;
  padding-left: 5px;
  padding-right: 5px;
  padding-top: 5px;
  width: 50px;
  height: 50px;
}
</style>

<template>
  <form action="/category/Science">
    <div class="center">
      <input type="text" placeholder="please input category" />
      <button type="submit" class="button submit-button">
        <img src="@/assets/images/icon/search.png" />
      </button>
    </div>
  </form>
</template>

<script setup lang="ts">
import { defineProps, Ref } from "vue";
import { Validation } from "@vuelidate/core";

const props = defineProps<{
  fieldName: string;
  validator: Ref<Validation>;
}>();
</script>

<style scoped>
.error {
  color: red;
  text-align: right;
  font-size: 10px;
  display: flex;
  justify-content: flex-end;
  font-style: italic;
}
</style>

<template>
  <template v-if="validator[fieldName].$error">
    <span
      class="error"
      v-for="error of validator[fieldName].$errors"
      :key="error.$uid"
      >{{ error.$message }}</span
    >
  </template>
</template>

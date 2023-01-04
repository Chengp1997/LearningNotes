<script setup lang="ts">
import ConfirmationTable from "@/components/ConfirmationTable.vue";

import { useCategoryStore } from "@/stores/CategoryStore";
const categoryStore = useCategoryStore();

import { useOrderDetailsStore } from "@/stores/OrderDetailsStore";
import { computed } from "vue";
const orderDetailsStore = useOrderDetailsStore();
const orderDetails = computed(() => {
  return orderDetailsStore.orderDetails;
});
</script>

<style scoped>
#confirmationView {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 1em auto;
}
ul {
  text-align: left;
}
ul > li {
  margin: 0.25em;
}
.message {
  margin: 10px auto;
  text-align: right;
}
.confirmationView {
  padding: 50px 10%;
  width: 100%;
  font-size: 18px;
}
.text_empty {
  font-size: 20px;
}
.align_position {
  margin-left: 250px;
}
</style>

<template>
  <div v-if="!orderDetailsStore.hasOrderDetails()" class="center">
    <span class="text_empty">No order is present</span>
  </div>
  <div id="confirmationView" v-else class="confirmationView">
    <div class="align_position">
      <ul>
        <li>Confirmation #: {{ orderDetails.order.confirmationNumber }}</li>
        <li>
          Time:
          {{ new Date(orderDetails.order.dateCreated).toLocaleTimeString() }}
        </li>
      </ul>
    </div>

    <div class="align_position">
      <confirmation-table> </confirmation-table>
    </div>
    <div class="align_position">
      <ul>
        <li>Customer Name: {{ orderDetails.customer.customerName }}</li>
        <li>Customer Address: {{ orderDetails.customer.address }}</li>
        <li>Customer email: {{ orderDetails.customer.email }}</li>
        <li>Customer Phone: {{ orderDetails.customer.phone }}</li>
        <li>
          Customer Credit card Number: **** **** ****
          {{ orderDetails.customer.ccNumber.slice(12, 16) }} ({{
            new Date(orderDetails.customer.ccExpDate).getMonth() + 1
          }}/{{ new Date(orderDetails.customer.ccExpDate).getFullYear() }})
        </li>
        <li>Surcharge: {{ $filters.priceFormat(500) }}</li>
        <li>Total: {{ $filters.priceFormat(orderDetails.order.amount) }}</li>
      </ul>
    </div>
  </div>
</template>

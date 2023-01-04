<script setup lang="ts">
import { useOrderDetailsStore } from "@/stores/OrderDetailsStore";
import { computed } from "vue";
import { BookItem } from "@/types";

const orderDetailsStore = useOrderDetailsStore();
const orderDetails = computed(() => {
  return orderDetailsStore.orderDetails;
});

const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase();
  name = name.replace(/'/g, "");
  name = name.replace(/ /g, "-");
  return `${name}.gif`;
};

function DateToLocalDate(date: Date) {
  return new Intl.DateTimeFormat("default", {
    year: "numeric",
    month: "numeric",
    day: "numeric",
    hour: "numeric",
    minute: "numeric",
    second: "numeric",
  }).format(date);
}
</script>

<style scoped>
table {
  border: 1px black solid;
  width: 600px;
  margin-top: 1em;
  margin-bottom: 1em;
  background-color: aliceblue;
}
td {
  display: table-cell;
  padding: 0.5em 0.5em;
  background-color: white;
  vertical-align: middle;
}

tr:nth-child(even) > td {
  background-color: lightgray;
}

td:nth-child(1) {
  text-align: left;
}

td:nth-child(2) {
  text-align: center;
}

td:nth-child(3) {
  text-align: right;
}
</style>

<template>
  <table>
    <tr>
      <th>Book Name</th>
      <th>Cover</th>
      <th>Item price</th>
      <th>Quantity</th>
      <th>Total Price</th>
    </tr>
    <tr v-for="(item, index) in orderDetails.books" :key="item.index">
      <td>{{ item.title }}</td>
      <td>
        <img
          class="confirmImage"
          :src="require('@/assets/images/books/' + bookImageFileName(item))"
          :alt="item.title"
          width="100"
          height="auto"
        />
      </td>
      <td>{{ $filters.priceFormat(item.price) }}</td>
      <td>{{ orderDetails.lineItems[index].quantity }}</td>
      <td>
        {{
          $filters.priceFormat(
            item.price * orderDetails.lineItems[index].quantity
          )
        }}
      </td>
    </tr>
  </table>
</template>

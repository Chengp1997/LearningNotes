<script setup lang="ts">
import { reactive, Ref } from "vue";
import useVuelidate from "@vuelidate/core";
import {
  email,
  helpers,
  maxLength,
  minLength,
  required,
} from "@vuelidate/validators";
import { useCartStore } from "@/stores/CartStore";
import { useCategoryStore } from "@/stores/CategoryStore";
const cartStore = useCartStore();
const cart = cartStore.cart;
const categoryStore = useCategoryStore();
import { isCreditCard, isMobilePhone } from "@/utils";
import CheckoutFieldError from "@/components/CheckoutFieldError.vue";
import router from "@/router";

const months: string[] = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

const form = reactive({
  name: "",
  address: "",
  phone: "",
  email: "",
  ccNumber: "",
  ccExpiryMonth: new Date().getMonth() + 1,
  ccExpiryYear: new Date().getFullYear(),
  checkoutStatus: "",
});

const rules = {
  name: {
    required: helpers.withMessage("Please provide a name.", required),
    minLength: helpers.withMessage(
      "Name must have at least 4 letters.",
      minLength(4)
    ),
    maxLength: helpers.withMessage(
      "Name can have at most 45 letters.",
      maxLength(45)
    ),
  },
  address: {
    required: helpers.withMessage("Please provide a address.", required),
    minLength: helpers.withMessage(
      "Address must have at least 4 letters.",
      minLength(4)
    ),
    maxLength: helpers.withMessage(
      "Address can have at most 45 letters.",
      maxLength(45)
    ),
  },
  phone: {
    required: helpers.withMessage("Please provide a phone number.", required),
    phone: helpers.withMessage(
      "Please provide a valid phone number.",
      (value: string) =>
        !helpers.req(value.trim()) || isMobilePhone(value.trim())
    ),
  },
  email: {
    required: helpers.withMessage("Please provide a email.", required),
    email: helpers.withMessage("Please provide a valid email.", email),
  },
  ccNumber: {
    required: helpers.withMessage(
      "Please provide credit card number.",
      required
    ),
    ccNumber: helpers.withMessage(
      "Please provide a valid credit card number.",
      (value: string) =>
        !helpers.req(value.trim()) || isCreditCard(value.trim())
    ),
  },
};
const $v = useVuelidate(rules, form);

async function submitOrder() {
  console.log("Submit order");
  const isFormCorrect = await $v.value.$validate();
  if (!isFormCorrect) {
    form.checkoutStatus = "ERROR";
  } else {
    form.checkoutStatus = "PENDING";
    await cartStore
      .placeOrder({
        name: form.name,
        address: form.address,
        phone: form.phone,
        email: form.email,
        ccNumber: form.ccNumber,
        ccExpiryMonth: form.ccExpiryMonth,
        ccExpiryYear: form.ccExpiryYear,
      })
      .then(() => {
        form.checkoutStatus = "OK";
        cartStore.clearCart();
        router.push({ name: "confirmation-view" });
      })
      .catch((reason) => {
        form.checkoutStatus = "SERVER_ERROR";
        console.log("Error placing order", reason);
      });
  }
}

function yearFrom(index: number) {
  return new Date().getFullYear() + index;
}
</script>

<style scoped>
.checkout-page {
  margin-top: 20px;
  margin-bottom: 20px;
  background: var(--primary-background-color);
  color: black;
}
.checkout-page-body {
  display: flex;
  padding: 1em;
}

.checkout-page-body-empty {
  padding: 1em;
}

form {
  display: flex;
  flex-direction: column;
}
.input-message {
  display: flex;
  justify-content: flex-end;
  flex-direction: column;
  margin-bottom: 0.5em;
}

.input-message input,
.input-message select {
  background-color: var(--secondary-background-color);
  margin-left: 0.5em;
  border: none;
}

.error {
  color: red;
  text-align: right;
  font-size: 10px;
  display: flex;
  justify-content: flex-end;
  font-style: italic;
}

.checkoutStatusBox {
  margin: 1em;
  padding: 1em;
  text-align: center;
}

.link {
  color: var(--button-text-color);
  text-decoration: none;
}
.checkout {
  margin-bottom: 50px;
  margin-top: 50px;
}

.message {
  margin: 10px auto;
  text-align: right;
}
.empty-text {
  font-size: 20px;
}
.flex-row {
  flex-direction: row;
}
</style>

<template>
  <div class="checkout-page center">
    <section class="checkout-page-body-empty" v-if="cart.empty">
      <div class="message">
        <span class="empty-text center">
          Your shopping cart is Empty, need to add an item to checkout
        </span>
      </div>
      <div class="buttons center">
        <button class="button secondary_button">
          <router-link
            :to="{
              name: 'category-view',
              params: { name: categoryStore.lastSelected },
            }"
            class="link"
          >
            <span>Continue Shopping</span>
          </router-link>
        </button>
      </div>
    </section>

    <section class="checkout-page-body" v-if="!cart.empty">
      <form @submit.prevent="submitOrder">
        <div class="input-message">
          <label for="name">Name</label>
          <input
            type="text"
            size="20"
            id="name"
            name="name"
            v-model.lazy="$v.name.$model"
          />
          <CheckoutFieldError field-name="name" :validator="$v" />
        </div>
        <div class="input-message">
          <label for="address">Address</label>
          <input
            type="text"
            size="20"
            id="address"
            name="address"
            v-model.lazy="$v.address.$model"
          />
          <CheckoutFieldError field-name="address" :validator="$v" />
        </div>

        <div class="input-message">
          <label for="phone">Phone</label>
          <input
            class="textField"
            type="text"
            size="20"
            id="phone"
            name="phone"
            v-model.lazy="$v.phone.$model"
          />
          <CheckoutFieldError field-name="phone" :validator="$v" />
        </div>

        <div class="input-message">
          <label for="email">Email</label>
          <input
            type="text"
            size="20"
            id="email"
            name="email"
            v-model.lazy="$v.email.$model"
          />
          <CheckoutFieldError field-name="email" :validator="$v" />
        </div>

        <div class="input-message">
          <label for="ccNumber">Credit card</label>
          <input
            type="text"
            size="20"
            id="ccNumber"
            name="ccNumber"
            v-model.lazy="$v.ccNumber.$model"
          />
          <CheckoutFieldError field-name="ccNumber" :validator="$v" />
        </div>

        <div class="input-message flex-row">
          <label>Exp Date</label>
          <select v-model="$v.ccExpiryMonth">
            <option
              v-for="(month, index) in months"
              :key="index"
              :value="index + 1"
            >
              {{ month }} ({{ index + 1 }})
            </option>
          </select>
          <select v-model="$v.ccExpiryYear">
            <option v-for="index in 16" :key="index" :value="index">
              {{ yearFrom(index - 1) }}
            </option>
          </select>
        </div>

        <div class="input-message">
          <span
            >Your credit card will be charged:{{
              $filters.priceFormat(cartStore.total)
            }}
          </span>
          <span
            >({{ $filters.priceFormat(cartStore.subtotal) }}+{{
              $filters.priceFormat(cartStore.surcharge)
            }}
            shipping)</span
          >
        </div>

        <div class="center">
          <input
            type="submit"
            name="submit"
            class="button primary_button"
            :disabled="form.checkoutStatus === 'PENDING'"
            value="Complete Purchase"
          />
        </div>
      </form>

      <section v-show="form.checkoutStatus !== ''" class="checkoutStatusBox">
        <div v-if="form.checkoutStatus === 'ERROR'">
          Error: Please fix the problems above and try again.
        </div>

        <div v-else-if="form.checkoutStatus === 'PENDING'">Processing...</div>

        <div v-else-if="form.checkoutStatus === 'OK'">Order placed...</div>

        <div v-else>An unexpected error occurred, please try again.</div>
      </section>
    </section>
  </div>
  <div v-if="!cart.empty" class="checkout center">
    <button class="button secondary_button">
      <router-link :to="'/cart'" class="link">
        Go back to the cart!
      </router-link>
    </button>
  </div>
</template>

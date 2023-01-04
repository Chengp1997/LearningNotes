import { createApp } from "vue";
import router from "./router";
import { createPinia } from "pinia";
import "@/assets/css/global.css"; // imports the global CSS before all other CSS files
import "@/assets/css/app.css";
import App from "./App.vue";
const pinia = createPinia();
const app = createApp(App);
const PriceFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
  minimumFractionDigits: 2,
});
app.config.globalProperties.$filters = {
  priceFormat(price: number) {
    return PriceFormatter.format(price / 100);
  },
};
app.use(router);
app.use(pinia);
app.mount("#app");

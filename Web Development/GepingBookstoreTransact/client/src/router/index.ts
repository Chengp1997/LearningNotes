import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import HomeView from "../views/HomeView.vue";
import CategoryView from "@/views/CategoryView.vue";
import CartView from "@/views/CartView.vue";
import CheckoutView from "@/views/CheckoutView.vue";
import ConfirmationView from "@/views/ConfirmationView.vue";
import PageNotFound from "@/views/PageNotFound.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    alias: ["/", "/home", "/index.html"],
    name: "home-view",
    component: HomeView,
  },
  {
    path: "/:pathMatch(.*)*",
    name: "page-not-found",
    component: PageNotFound,
  },
  {
    path: "/category/:name",
    name: "category-view",
    component: CategoryView,
    props: true,
  },
  {
    path: "/category",
    redirect: "category/Science",
  },
  {
    path: "/cart",
    name: "cart-view",
    component: CartView,
  },

  {
    path: "/checkout",
    name: "checkout-view",
    component: CheckoutView,
  },
  {
    path: "/confirmation",
    name: "confirmation-view",
    component: ConfirmationView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;

import { defineStore } from "pinia";
import { CategoryItem } from "@/types";
import { apiUrl } from "@/services/ApiService";

//name, state, actions, getter
export const useCategoryStore = defineStore("CategoryStore", {
  state: () => ({
    //get global object that we want to use
    categoryList: [] as CategoryItem[],
    lastSelected: "Science" as string,
  }),
  actions: {
    async fetchCategories() {
      const url = apiUrl + "categories";
      this.categoryList = await fetch(url).then((response) => response.json());
    },
    setLastSelectedCategory(category: string) {
      this.lastSelected = category;
      console.log("last selected" + this.lastSelected);
    },
    containsCategory(name: string) {
      // console.log(name == this.lastSelected);
      return (
        name === "Science" ||
        this.categoryList.find((item) => item.name === name) != null
      );
    },
  },
  // getters
});

import { defineStore } from "pinia";
import { BookItem, CategoryItem } from "@/types";
import { apiUrl } from "@/services/ApiService";
import { useCategoryStore } from "@/stores/CategoryStore";

export const useBookStore = defineStore("BookStore", {
  state: () => ({
    bookList: [] as BookItem[],
  }),
  actions: {
    async fetchBooks(categoryName: string) {
      const categoryStore = useCategoryStore();
      let selectedCategoryName = categoryName;
      const selectedCategory = categoryStore.categoryList?.find(
        (category) => category.name === categoryName
      );
      if (selectedCategory) {
        selectedCategoryName = selectedCategory.name;
      }

      const url =
        apiUrl + "categories/name/" + selectedCategoryName + "/books/";
      this.bookList = await fetch(url).then((response) => response.json());
    },
  },
  // getters
});

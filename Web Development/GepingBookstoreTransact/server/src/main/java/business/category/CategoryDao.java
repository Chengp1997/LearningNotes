package business.category;

import java.util.List;

public interface CategoryDao {

    List<Category> findAll();

    Category findByCategoryId(long categoryId);

    Category findByName(String categoryName);
}

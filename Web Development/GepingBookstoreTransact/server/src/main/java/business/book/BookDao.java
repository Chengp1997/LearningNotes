package business.book;

import java.util.List;

public interface BookDao {

    Book findByBookId(long bookId);

    List<Book> findByCategoryId(long categoryId);

    List<Book> findRandomByCategoryId(long categoryId, int limit);

}

package bookshop.dao;

import bookshop.pojo.Book;

import java.util.List;

/**
 * @author Alitar
 * @date 2023-02-17 19:30
 */
public interface BookDao {

    List<Book> getBookList();
    Book getBook(Integer id);
}

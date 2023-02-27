package bookshop.service;

import bookshop.pojo.Book;
import java.util.List;

/**
 * @author Alitar
 * @date 2023-02-17 19:34
 */
public interface BookService {
    List<Book> getBookList();
    //根据id获取book详情信息
    Book getBook(Integer id);
}

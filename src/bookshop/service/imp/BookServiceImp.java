package bookshop.service.imp;

import bookshop.dao.BookDao;
import bookshop.pojo.Book;
import bookshop.service.BookService;

import java.util.List;

/**
 * @author Alitar
 * @date 2023-02-17 19:35
 */
public class BookServiceImp implements BookService {
    private BookDao bookDao ;
    @Override
    public List<Book> getBookList() {
        return bookDao.getBookList();
    }

    @Override
    public Book getBook(Integer id) {
        return bookDao.getBook(id);
    }
}
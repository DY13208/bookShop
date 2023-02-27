package bookshop.dao.imp;

import bookshop.dao.BookDao;
import bookshop.mySpringMvc.JDBCUtil.getDao;
import bookshop.pojo.Book;


import java.util.List;

/**
 * @author Alitar
 * @date 2023-02-17 19:30
 */
public class BookDaoImp extends getDao<Book> implements BookDao {
    @Override
    public List<Book> getBookList() {
        return executeQuery("select * from t_book where bookStatus = 0");
    }

    @Override
    public Book getBook(Integer id) {
        return load("select * from t_book where id = ?",id);
    }
}
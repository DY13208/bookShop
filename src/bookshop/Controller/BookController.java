package bookshop.Controller;

import bookshop.pojo.Book;
import bookshop.service.BookService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Alitar
 * @date 2023-02-17 19:40
 */
public class BookController {

    private BookService bookService;

    public String index(HttpSession session){

        List<Book> bookList = bookService.getBookList();

        session.setAttribute("bookList",bookList);

        return "index";

    }
}
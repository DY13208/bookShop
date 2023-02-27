package bookshop.test;

import bookshop.pojo.Book;
import bookshop.service.BookService;
import bookshop.service.imp.BookServiceImp;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Alitar
 * @date 2023-02-17 20:12
 */
public class testJdbc {
    @Test
    public void test(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        String[] split = format.split("-");
        String s1 = "";
        for (String s:split) {
            s1+=s;
        }
          split  = s1.split(" ");
        s1="";
        for (String s:split) {
            s1+=s;
        }
        split = s1.split(":");
        s1="";
        for (String s:split) {
            s1+=s;
        }
        System.out.println(s1);
    }
}
package bookshop.Controller;

import bookshop.pojo.Book;
import bookshop.pojo.Cart;
import bookshop.pojo.User;
import bookshop.service.BookService;
import bookshop.service.CartItemService;
import bookshop.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * @author Alitar
 * @date 2023-02-17 17:13
 */
public class UserController {
    private UserService userService;
    private CartItemService cartItemService;

    public String login(String uname,String pwd,HttpSession session){

        User login = userService.login(uname, pwd);

         if (login!=null){
            Cart cart = cartItemService.getCart(login);
            login.setCart(cart);
            session.setAttribute("currUser",login);
            return "redirect:book.do";
        }
        return "user/login";

    }
    public String regist(String registName, String registPwd, String registEmail, String registKaptch, HttpSession session, HttpServletResponse resp){
        Object kaptcha_session_key =   session.getAttribute("KAPTCHA_SESSION_KEY");
        if (kaptcha_session_key==null || !registKaptch.equals(kaptcha_session_key)){
//            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = null;
            try {
                out = resp.getWriter();
                out.println("<script language='javascript'>alert('验证码不正确！');window.location.href='page.do?operate=page&page=user/regist';</script>");
            } catch (IOException e) {
                e.printStackTrace();
            }
//            out.println("<script language='javascript'>alert('验证码不正确！');</script>");
            //return "user/regist";
            return null;
        }else {
            if (registKaptch.equals(kaptcha_session_key)){
                userService.add(new User(registName,registPwd,registEmail));
                return "user/login";
            }

        }
        return "user/login";
    }
    public String ckUname(String uname){
        User userName = userService.getUserName(uname);
        if (userName!=null){
            //用户名已经被占用
            return "json:{'uname':'1'}";
//            return "ajax:1";
        }else {
            //用户名可以注册
            return "json:{'uname':'0'}";
//            return "ajax:0";
        }

    }
}
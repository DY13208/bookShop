package bookshop.Controller;

import bookshop.pojo.Book;
import bookshop.pojo.Cart;
import bookshop.pojo.CartItem;
import bookshop.pojo.User;
import bookshop.service.CartItemService;
import com.google.gson.Gson;

import javax.servlet.http.HttpSession;

/**
 * @author Alitar
 * @date 2023-02-17 20:34
 */
public class CartController {

    private CartItemService cartItemService;

    //加载当前用户的购物车信息
    public String index(HttpSession session){
        User currUser = (User) session.getAttribute("currUser");
        Cart cart = cartItemService.getCart(currUser);
        currUser.setCart(cart);
        session.setAttribute("currUser",currUser);
        return "cart/cart";

    }
    public String addCart(Integer bookId, HttpSession session){
        User currUser = (User) session.getAttribute("currUser");
        CartItem cartItem = new CartItem(new Book(bookId),1,currUser);


        //特指定的图书添加到当前用户的购物车中
        cartItemService.addOrUpdateCartItem(cartItem,currUser.getCart());
        return "redirect:cart.do";
    }
    public String editCart(Integer  cartItemId,Integer buyCount){
        cartItemService.updateCartItem(new CartItem(cartItemId,buyCount));
        return "";//redirect:cart.do
    }
    public String getCart(HttpSession session){
        User currUser = (User) session.getAttribute("currUser");
        Cart cart = cartItemService.getCart(currUser);
        cart.getTotalBookCount();
        cart.getTotalCount();
        cart.getTotalMoney();
        Gson gson = new Gson();

        String cartJson =gson.toJson(cart);

        return "json:"+cartJson;
    }
}
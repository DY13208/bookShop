package bookshop.service.imp;

import bookshop.dao.CartItemDao;
import bookshop.pojo.Book;
import bookshop.pojo.Cart;
import bookshop.pojo.CartItem;
import bookshop.pojo.User;
import bookshop.service.BookService;
import bookshop.service.CartItemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alitar
 * @date 2023-02-17 20:49
 */
public class CartItemServiceImp implements CartItemService {
    private CartItemDao cartItemDao;
    private BookService bookService;
    @Override
    public void addCartItem(CartItem cartItem) {
        cartItemDao.addCartItem(cartItem);
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        cartItemDao.updateCartItem(cartItem);
    }

    @Override
    public void addOrUpdateCartItem(CartItem cartItem , Cart cart) {
        //1.如果当前用户的购物车中已经存在这个图书了，那么购物车中这本图书的数量+1
        // 2.否则，在我的购物车中新增一个这本图书的cartItem，数量是1
        if (cart!=null){
            Map<Integer, CartItem> cartItemMap = cart.getCartItemMap();
            if (cartItemMap==null){
                cartItemMap = new HashMap<>();
            }
            //当前购物车有对应的图书
            if (cartItemMap.containsKey(cartItem.getBook().getId())){
                CartItem cartItemTemp = cartItemMap.get(cartItem.getBook().getId());
                cartItemTemp.setBuyCount(cartItemTemp.getBuyCount()+1);
                updateCartItem(cartItemTemp);
            }else {
                addCartItem(cartItem);
            }
        }else {
            addCartItem(cartItem);
        }
        //判断当前用户的赠物车中是否有这本书的cartItem，有->update ，无->add

    }

    @Override
    public List<CartItem> getCartItemList(User user) {
        List<CartItem> cartList = cartItemDao.getCartList(user);
        for (CartItem cartItem:cartList) {
           Book book = bookService.getBook(cartItem.getBook().getId());
           cartItem.setBook(book);
           cartItem.getXj();
        }
        return cartList;
    }

    @Override
    public Cart getCart(User user) {
        List<CartItem> cartList = getCartItemList(user);
        Map<Integer,CartItem> cartItemMap = new HashMap<>();
        for (CartItem cartItem:cartList){
            cartItemMap.put(cartItem.getBook().getId(),cartItem);
        }
        Cart cart = new Cart();
        cart.setCartItemMap(cartItemMap);
        return cart;
    }
}
package bookshop.service;

import bookshop.pojo.Cart;
import bookshop.pojo.CartItem;
import bookshop.pojo.User;

import java.util.List;

/**
 * @author Alitar
 * @date 2023-02-17 20:49
 */
public interface CartItemService {
    void addCartItem(CartItem cartItem);
    void updateCartItem(CartItem cartItem);
    void addOrUpdateCartItem(CartItem cartItem, Cart cart);
    //获取指定用户的所有购物车项列表(需要注意的是，这个方法内部查询的时候，会book的详细信息设置进去)
    List<CartItem> getCartItemList(User user);

    //加载指定用户的购物车信息
    Cart getCart(User user);
}

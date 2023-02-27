package bookshop.dao;

import bookshop.pojo.CartItem;
import bookshop.pojo.User;

import java.util.List;

/**
 * @author Alitar
 * @date 2023-02-17 20:38
 */
public interface CartItemDao {
    //新增购物车项
    void addCartItem(CartItem cartItem);
    //修改特定购物车项
    void updateCartItem(CartItem cartItem);
    //获取指定用户的所有购物车项
    List<CartItem> getCartList(User user);
    //删除指定的购物车项
    void delCartItem(CartItem cartItem);
}

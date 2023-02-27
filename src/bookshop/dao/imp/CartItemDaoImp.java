package bookshop.dao.imp;

import bookshop.dao.CartItemDao;
import bookshop.mySpringMvc.JDBCUtil.getDao;
import bookshop.pojo.CartItem;
import bookshop.pojo.User;


import java.util.List;

/**
 * @author Alitar
 * @date 2023-02-17 20:41
 */
public class CartItemDaoImp extends getDao<CartItem> implements CartItemDao {
    @Override
    public void addCartItem(CartItem cartItem) {
        executeUpdate("insert into t_cart_item values(0,?,?,?)",cartItem.getBook().getId(),cartItem.getBuyCount(),cartItem.getUserBean().getId());
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        executeUpdate("update t_cart_item set buyCount = ? where id = ?",cartItem.getBuyCount(),cartItem.getId());
    }

    @Override
    public List<CartItem> getCartList(User user) {
        return executeQuery("select * from t_cart_item where userBean = ? ",user.getId());
    }

    @Override
    public void delCartItem(CartItem cartItem) {
        executeUpdate("delete from t_cart_item where id = ?",cartItem.getId());
    }
}
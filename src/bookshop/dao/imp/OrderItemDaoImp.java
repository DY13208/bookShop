package bookshop.dao.imp;

import bookshop.dao.OrderItemDao;
import bookshop.mySpringMvc.JDBCUtil.getDao;
import bookshop.pojo.OrderItem;

/**
 * @author Alitar
 * @date 2023-02-20 16:40
 */
public class OrderItemDaoImp extends getDao<OrderItem> implements OrderItemDao {
    @Override
    public void addOrderItem(OrderItem orderItem) {
            executeUpdate("insert into t_order_item values(0,?,?,?)",orderItem.getBook().getId(),orderItem.getBuyCount(),orderItem.getOrderBean().getId());
    }
}
package bookshop.dao;

import bookshop.pojo.OrderItem;

/**
 * @author Alitar
 * @date 2023-02-20 16:39
 */
public interface OrderItemDao {
    //添加订单项
    void addOrderItem(OrderItem orderItem);
}

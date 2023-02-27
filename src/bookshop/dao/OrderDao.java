package bookshop.dao;

import bookshop.pojo.OrderBean;
import bookshop.pojo.User;

import java.util.List;

/**
 * @author Alitar
 * @date 2023-02-20 16:35
 */
public interface OrderDao {
    //添加订单
    void addOrderBean(OrderBean orderBean);
    List<OrderBean> getOrderList(User user);
    Integer getOrderBookTotalCount(OrderBean orderBean);
}

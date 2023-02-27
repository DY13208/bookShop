package bookshop.service;

import bookshop.pojo.OrderBean;
import bookshop.pojo.OrderItem;
import bookshop.pojo.User;

import java.util.List;

/**
 * @author Alitar
 * @date 2023-02-20 16:49
 */

public interface OrderBeanService {
    //结账功能
    void addOrderBean(OrderBean orderBean);
    List<OrderBean> getOrderList(User user);
}

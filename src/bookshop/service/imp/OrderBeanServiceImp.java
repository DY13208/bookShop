package bookshop.service.imp;

import bookshop.dao.CartItemDao;
import bookshop.dao.OrderDao;
import bookshop.dao.OrderItemDao;
import bookshop.pojo.CartItem;
import bookshop.pojo.OrderBean;
import bookshop.pojo.OrderItem;
import bookshop.pojo.User;
import bookshop.service.OrderBeanService;

import java.util.List;
import java.util.Map;

/**
 * @author Alitar
 * @date 2023-02-20 16:49
 */
public class OrderBeanServiceImp implements OrderBeanService {
    private OrderDao orderDao;
    private OrderItemDao orderItemDao;
    private CartItemDao cartItemDao;
    private Integer getOrderBookTotlCount;
    @Override
    public void addOrderBean(OrderBean orderBean) {
        //1) 订单表添加一条记录
        orderDao.addOrderBean(orderBean); //执行完这一步，orderBean中的id是有值的
        //2) 订单详情表添加7条记录
        User orderUser = orderBean.getOrderUser();
        Map<Integer, CartItem> cartItemMap = orderUser.getCart().getCartItemMap();
        List<OrderItem> orderItemList = orderBean.getOrderItemList();
        for (CartItem cartItem:cartItemMap.values()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setBuyCount(cartItem.getBuyCount());
            orderItem.setOrderBean(orderBean);
            orderItemDao.addOrderItem(orderItem);
        }
        //3) 购物车项表中需要删除对应的7条记录
//        User orderUser = orderBean.getOrderUser();
//        Map<Integer, CartItem> cartItemMap = orderUser.getCart().getCartItemMap();
        for (CartItem cartItem:cartItemMap.values()) {
            cartItemDao.delCartItem(cartItem);
        }
    }

    @Override
    public List<OrderBean> getOrderList(User user) {
        List<OrderBean> orderList = orderDao.getOrderList(user);
        for (OrderBean orderBean:orderList) {
            Integer orderBookTotalCount = orderDao.getOrderBookTotalCount(orderBean);
            orderBean.setTotalBookCount(orderBookTotalCount);
        }
        return orderList;
    }
}
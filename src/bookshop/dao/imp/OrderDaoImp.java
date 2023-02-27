package bookshop.dao.imp;

import bookshop.dao.OrderDao;
import bookshop.mySpringMvc.JDBCUtil.getDao;
import bookshop.pojo.OrderBean;
import bookshop.pojo.User;


import java.math.BigDecimal;
import java.util.List;

/**
 * @author Alitar
 * @date 2023-02-20 16:38
 */
public class OrderDaoImp extends getDao<OrderBean> implements OrderDao {
    @Override
    public void addOrderBean(OrderBean orderBean) {
       int orderBeanId = executeUpdate("insert into t_order values(0,?,?,?,?,?)",orderBean.getOrderNo(),orderBean.getOrderDate(),orderBean.getOrderUser().getId(),orderBean.getOrderMoney(),orderBean.getOrderStatus());
       orderBean.setId(orderBeanId);
    }

    @Override
    public List<OrderBean> getOrderList(User user) {
        return executeQuery("SELECT * FROM t_order WHERE orderUser = ? ",user.getId());
    }

    @Override
    public Integer getOrderBookTotalCount(OrderBean orderBean) {
        String sql ="SELECT SUM(t3.buyCount) AS totalBookCount , t3.orderBean FROM " +
                "(" +
                "SELECT t1.id , t2.buyCount , t2.orderBean FROM t_order t1 INNER JOIN t_order_item t2 " +
                "ON t1.id = t2.orderBean WHERE t1.orderUser = ? " +
                ") t3 WHERE t3.orderBean = ? GROUP BY t3.orderBean" ;
        return ((BigDecimal) executeComplexQuery(sql,orderBean.getOrderUser().getId(),orderBean.getId())[0]).intValue();
    }
}
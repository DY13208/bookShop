package bookshop.Controller;

import bookshop.pojo.OrderBean;
import bookshop.pojo.User;
import bookshop.service.OrderBeanService;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Alitar
 * @date 2023-02-20 17:01
 */
public class OrderController {
    private OrderBeanService orderBeanService;
    //结账
    public String checkout(HttpSession session){

        OrderBean orderBean = new OrderBean();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        String[] split = format.split("-");
        String s1 = "";
        for (String s:split) {
            s1+=s;
        }
        split  = s1.split(" ");
        s1="";
        for (String s:split) {
            s1+=s;
        }
        split = s1.split(":");
        s1="";
        for (String s:split) {
            s1+=s;
        }

        orderBean.setOrderNo(UUID.randomUUID().toString()+"_"+s1);
        orderBean.setOrderDate(LocalDateTime.now());
        User currUser = (User) session.getAttribute("currUser");
        orderBean.setOrderUser(currUser);
        orderBean.setOrderMoney(currUser.getCart().getTotalMoney());
        orderBean.setOrderStatus(0);

        orderBeanService.addOrderBean(orderBean);
        return "redirect:book.do";
    }
    public String getOrderList(HttpSession session){
        User currUser = (User) session.getAttribute("currUser");
        List<OrderBean> orderList = orderBeanService.getOrderList(currUser);
        currUser.setOrderBeanList(orderList);
        session.setAttribute("currUser",currUser);
        return "order/order";
    }
}
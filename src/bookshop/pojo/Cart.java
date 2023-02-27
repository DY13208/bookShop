package bookshop.pojo;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * @author Alitar
 * @date 2023-02-17 20:53
 */
public class Cart {
    private Map<Integer,CartItem> cartItemMap ;     //购物车中购物车项的集合 , 这个Map集合中的key是Book的id
    private Double totalMoney ;                     //购物车的总金额
    private Integer totalCount ;                    //购物车中购物项的数量
    private Integer totalBookCount ;                //购物车中书本的总数量，而不是购物车项的总数量
    public Cart(){}

    public Map<Integer, CartItem> getCartItemMap() {
        return cartItemMap;
    }

    public void setCartItemMap(Map<Integer, CartItem> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }

    public Double getTotalMoney() {
        totalMoney = 0.0;
        BigDecimal bigDecimalTotalMoney;
        BigDecimal bigDecimalPrice;
        BigDecimal bigDecimalBuyCount;
        if(cartItemMap!=null && cartItemMap.size()>0){
            Set<Map.Entry<Integer, CartItem>> entries = cartItemMap.entrySet();
            for(Map.Entry<Integer,CartItem> cartItemEntry : entries){
                CartItem cartItem = cartItemEntry.getValue();
                bigDecimalTotalMoney = new BigDecimal(""+totalMoney);
                bigDecimalPrice = new BigDecimal(""+cartItem.getBook().getPrice());
                bigDecimalBuyCount = new BigDecimal(""+cartItem.getBuyCount());
//                totalMoney = totalMoney + cartItem.getBook().getPrice() * cartItem.getBuyCount() ;
                BigDecimal multiply = bigDecimalPrice.multiply(bigDecimalBuyCount);
                BigDecimal add = bigDecimalTotalMoney.add(multiply);
                totalMoney = add.doubleValue();
            }
        }
        return totalMoney;
    }

    public Integer getTotalCount() {
        totalCount = 0 ;
        if(cartItemMap!=null && cartItemMap.size()>0){
            totalCount = cartItemMap.size() ;
        }
        return totalCount;
    }

    public Integer getTotalBookCount() {
        totalBookCount = 0 ;
        if(cartItemMap!=null && cartItemMap.size()>0){
            for (CartItem cartItem : cartItemMap.values()){
                totalBookCount = totalBookCount + cartItem.getBuyCount() ;
            }
        }
        return totalBookCount;
    }
}

package businessObjects;

import java.util.ArrayList;

public class OrderHandler {

    public static ArrayList<Order> getAllOrders(){
        ArrayList orders = Order.importAllOrders();
        /*for (Order o : orders){
            orders.add(new OrderInfo(o.getUser, o.getOrderNr(), o.getOrderItems(), o.getOrderStatus()))
        }*/
        return orders;
    }
}

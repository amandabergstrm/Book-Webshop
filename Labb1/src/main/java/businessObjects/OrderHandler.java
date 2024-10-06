package businessObjects;

import database.DbOrder;
import ui.BookInfo;
import ui.OrderInfo;
import ui.OrderItemInfo;

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class OrderHandler {


    public static void createOrder(OrderInfo orderInfo) {
        ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
        ArrayList<OrderItemInfo> orderItemInfos = orderInfo.getOrderItemInfo();

        for(OrderItemInfo o: orderItemInfos){
            orderItems.add(new OrderItem(o.getItemId(), o.getNrOfItems()));
        }
        Order orderObj = new Order(orderInfo.getUserEmail(), orderItems);
        Order.createOrder(orderObj);
    }
    /*public static ArrayList<OrderInfo> getAllOrders(){
        ArrayList<DbOrder> orders = Order.importAllOrders();
        if(orders == null) {
            System.out.println("INGA ORDRAR FINNS");
        }

        ArrayList<OrderInfo> orderInfo = new ArrayList<OrderInfo>();
        ArrayList<OrderItemInfo> orderItemInfos = new ArrayList<OrderItemInfo>();
        for(Order o: orders){
            ArrayList<OrderItem> orderItems = o.getOrderItems();
            for(OrderItem b: orderItems) {
                OrderItemInfo orderItemInfo = new OrderItemInfo(b.getItemId(), b.getNrOfItems());
                orderItemInfos.add(orderItemInfo);
            }
        }

        for (Order o : orders){
            orderInfo.add(new OrderInfo(o.getUserEmail(), o.getOrderNr(), orderItemInfos, o.getOrderStatus()));
        }
        return orderInfo;
    }*/

    public static ArrayList<OrderInfo> getAllOrders() {
        ArrayList<DbOrder> orders = Order.importAllOrders();
        if (orders == null) {
            System.out.println("INGA ORDRAR FINNS");
        }
        return convertOrderToOrderInfo(orders);
    }

    public static ArrayList<OrderInfo> getUserOrders(String email) {
        ArrayList<DbOrder> orders = Order.searchUserOrders(email);
        return convertOrderToOrderInfo(orders);
    }

    public static void updateOrderStatus(OrderInfo orderInfo, OrderStatus newStatus) {
        Order orderObj = new Order(orderInfo.getUserEmail(), orderInfo.getOrderNr(), converOrderItemInfoToOrderItem(orderInfo.getOrderItemInfo()), newStatus);
        Order.updateOrderStatus(orderObj);
    }

    public static ArrayList<OrderItem> converOrderItemInfoToOrderItem(ArrayList<OrderItemInfo> orderItemInfos){
        ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
        for(OrderItemInfo o: orderItemInfos){
            orderItems.add(new OrderItem(o.getItemId(), o.getNrOfItems()));
        }
        return orderItems;
    }

    //Snygga till senare, anropa converOrderItemInfoToOrderItem istället
    private static ArrayList<OrderInfo> convertOrderToOrderInfo(ArrayList<DbOrder> orders) {
        ArrayList<OrderInfo> orderInfoList = new ArrayList<>();

        for (Order o : orders) {
            ArrayList<OrderItemInfo> orderItemInfos = new ArrayList<>();

            ArrayList<OrderItem> orderItems = o.getOrderItems();
            for (OrderItem item : orderItems) {
                OrderItemInfo orderItemInfo = new OrderItemInfo(item.getItemId(), item.getNrOfItems());
                orderItemInfos.add(orderItemInfo);
            }
            orderInfoList.add(new OrderInfo(o.getUserEmail(), o.getOrderNr(), orderItemInfos, o.getOrderStatus()));
        }
        return orderInfoList;
    }
}

package businessObjects;

import ui.OrderInfo;
import ui.OrderItemInfo;

import java.util.ArrayList;
import java.util.Collection;

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

    public static Collection<OrderInfo> getAllOrders() {
        Collection orders = Order.importAllOrders();
        if (orders == null) {
            System.out.println("INGA ORDRAR FINNS");
        }
        return convertOrderToOrderInfo(orders);
    }

    public static ArrayList<OrderInfo> getUserOrders(String email) {
        Collection orders = Order.searchUserOrders(email);
        return convertOrderToOrderInfo(orders);
    }

    public static void updateOrderStatus(int orderNr, String newStatus) {
        //Order orderObj = new Order(orderInfo.getUserEmail(), orderInfo.getOrderNr(), convertOrderItemInfoToOrderItem(orderInfo.getOrderItemInfo()), newStatus);
        Order.updateOrderStatus(orderNr, newStatus);
    }

    public static ArrayList<OrderItem> convertOrderItemInfoToOrderItem(ArrayList<OrderItemInfo> orderItemInfos){
        ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
        for(OrderItemInfo o: orderItemInfos){
            orderItems.add(new OrderItem(o.getItemId(), o.getNrOfItems()));
        }
        return orderItems;
    }

    //Snygga till senare, anropa converOrderItemInfoToOrderItem istället
    private static ArrayList<OrderInfo> convertOrderToOrderInfo(Collection<Order> orders) {
        ArrayList<OrderInfo> orderInfoList = new ArrayList<>();

        for (Order o : orders) {
            ArrayList<OrderItem> orderItems = o.getOrderItems();
            ArrayList<OrderItemInfo> orderItemInfos = new ArrayList<>();

            for (OrderItem item : orderItems) {
                OrderItemInfo orderItemInfo = new OrderItemInfo(item.getItemId(), item.getNrOfItems());
                orderItemInfos.add(orderItemInfo);
            }
            orderInfoList.add(new OrderInfo(o.getUserEmail(), o.getOrderNr(), orderItemInfos, o.getOrderStatus()));
        }
        return orderInfoList;
    }
}

package businessObjects;

import database.DbOrder;
import java.util.ArrayList;

public class Order {
    private final String userEmail;
    private int orderNr;
    private OrderStatus status;
    private ArrayList<OrderItem> orderItems;

    public static void createOrder(Order order) {
        DbOrder.executeOrderInsert(order);
    }
    public static ArrayList<DbOrder> importAllOrders() {
        return DbOrder.importAllOrders();
    }
    public static void updateOrderStatus(int orderNr, String status) {
        DbOrder.executeOrderUpdate(orderNr, status);
    }
    public static ArrayList<DbOrder> searchUserOrders(String email) {
        return DbOrder.searchUserOrders(email);
    }

    protected Order (String userEmail, ArrayList<OrderItem> orderItems) {
        this.userEmail = userEmail;
        this.orderItems = orderItems;
        this.status = OrderStatus.Pending;
    }

    protected Order (String userEmail, int orderNr, ArrayList<OrderItem> orderItems, OrderStatus status) {
        this.userEmail = userEmail;
        this.orderNr = orderNr;
        this.orderItems = orderItems;
        this.status = status;
    }

    public String getUserEmail() { return userEmail; }
    public int getOrderNr() { return orderNr; }
    public OrderStatus getOrderStatus() { return status; }
    public ArrayList<OrderItem> getOrderItems () { return orderItems; }
}
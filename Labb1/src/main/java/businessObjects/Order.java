package businessObjects;
import database.DbBook;
import database.DbOrder;
import ui.UserInfo;

import java.util.ArrayList;
public class Order {

    private final User user;

    private final int orderNr;

    private OrderStatus status;

    private ArrayList<OrderItem> orderItems;

    //private int totalSum;

    public static ArrayList<DbOrder> importAllOrders() {
        return DbOrder.importAllOrders();
    }
    public Order (User user, int orderNr, ArrayList<OrderItem> orderItems) {
        this.user = user;
        this.orderNr = orderNr;
        this.orderItems = orderItems;
        this.status = OrderStatus.Pending;
        /*for (OrderItem b: orderItems){
            this.totalSum += b.getPrice();
        }*/
    }
    public Order (User user, int orderNr, ArrayList<OrderItem> orderItems, OrderStatus status) {
        this.user = user;
        this.orderNr = orderNr;
        this.orderItems = orderItems;
        this.status = status;
        //this.totalSum = totalSum;
    }

    public User getUser() { return user; }
    public int getOrderNr() { return orderNr; }
    public OrderStatus getOrderStatus() { return status; }
    public ArrayList<OrderItem> getOrderItems () { return orderItems; }
}

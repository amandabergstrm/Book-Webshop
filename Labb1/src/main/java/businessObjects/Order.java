package businessObjects;
import database.DbBook;
import database.DbOrder;
import ui.UserInfo;

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

    //när man SKAPAR en ny order
    protected Order (String userEmail, ArrayList<OrderItem> orderItems) {
        this.userEmail = userEmail;
        this.orderItems = orderItems;
        this.status = OrderStatus.Pending;
        /*for (OrderItem b: orderItems){
            this.totalSum += b.getPrice();
        }*/
    }

    //när man HÄMTAR en ny order
    protected Order (String userEmail, int orderNr, ArrayList<OrderItem> orderItems, OrderStatus status) {
        this.userEmail = userEmail;
        this.orderNr = orderNr;
        this.orderItems = orderItems;
        this.status = status;
        //this.totalSum = totalSum;
    }

    public String getUserEmail() { return userEmail; }
    public int getOrderNr() { return orderNr; }
    public OrderStatus getOrderStatus() { return status; }
    public ArrayList<OrderItem> getOrderItems () { return orderItems; }
}

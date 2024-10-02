package businessObjects;
import java.util.ArrayList;
public class Order {

    private final User user;

    private final int orderNr;

    private ArrayList<OrderItem> orderItems;

    private OrderStatus status;

    //private int totalSum;

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
}

package ui;

import businessObjects.OrderItem;
import businessObjects.OrderStatus;
import businessObjects.User;

import java.util.ArrayList;

public class OrderInfo {

    private final String userEmail;

    private int orderNr;

    private OrderStatus status;

    private ArrayList<OrderItemInfo> orderItemInfo;

    //Konstruktor för när man ska HÄMTA en ny order
    public OrderInfo (String userEmail, int orderNr, ArrayList<OrderItemInfo> orderItemInfo, OrderStatus status) {
        this.userEmail = userEmail;
        this.orderNr = orderNr;
        this.orderItemInfo = orderItemInfo;
        this.status = status;
        /*for (OrderItem b: orderItems){
            this.totalSum += b.getPrice();
        }*/
    }
    //Konstruktor för när man ska SKAPA en order från databasen och skapa ett objekt

    public OrderInfo (String userEmail, ArrayList<OrderItemInfo> orderItemInfo) {
        this.userEmail = userEmail;
        this.status = status;
        this.orderItemInfo = orderItemInfo;
        this.status = OrderStatus.Pending;
        //this.totalSum = totalSum;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getOrderNr() {
        return orderNr;
    }

    public OrderStatus getOrderStatus() {
        return status;
    }

    public ArrayList<OrderItemInfo> getOrderItemInfo() {
        return orderItemInfo;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setOrderItems(ArrayList<OrderItemInfo> orderItems) {
        this.orderItemInfo = orderItems;
    }

    @Override
    public String toString() {
        return "OrderInfo {" +
                "User email'" + userEmail + '\'' +
                ", Order nr='" + orderNr + '\'' +
                ", Status='" + status + '\'' +
                ", Order items" + orderItemInfo.toString() + '\'' +
                '}';
    }
}

package ui;

import businessObjects.OrderItem;
import businessObjects.OrderStatus;
import businessObjects.User;

import java.util.ArrayList;

public class OrderInfo {

    private final User user;

    private final int orderNr;

    private OrderStatus status;

    private ArrayList<OrderItem> orderItems;

    //Konstruktor för när man ska SKAPA en ny order
    public OrderInfo (User user, int orderNr, ArrayList<OrderItem> orderItems) {
        this.user = user;
        this.orderNr = orderNr;
        this.orderItems = orderItems;
        this.status = OrderStatus.Pending;
        /*for (OrderItem b: orderItems){
            this.totalSum += b.getPrice();
        }*/
    }
    //Konstruktor för när man ska HÄMTA en order från databasen och skapa ett objekt
    public OrderInfo (User user, int orderNr, ArrayList<OrderItem> orderItems, OrderStatus status) {
        this.user = user;
        this.orderNr = orderNr;
        this.orderItems = orderItems;
        this.status = status;
        //this.totalSum = totalSum;
    }
}

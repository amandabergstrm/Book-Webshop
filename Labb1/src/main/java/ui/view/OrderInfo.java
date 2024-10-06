package ui.view;

import businessObjects.OrderStatus;
import java.util.ArrayList;

/**
 * The {@code OrderInfo} class represents the details of an order in the system.
 * It contains information about the user who placed the order, the order number, status, and items in the order.
 */
public class OrderInfo {
    private final String userEmail;
    private int orderNr;
    private OrderStatus status;
    private ArrayList<OrderItemInfo> orderItemInfo;

    /**
     * Constructs an {@code OrderInfo} object with the provided order details, including order number and status.
     *
     * @param userEmail      the email of the user who placed the order
     * @param orderNr        the unique identifier for the order
     * @param orderItemInfo  the list of items in the order
     * @param status         the status of the order (e.g., Pending, Shipped)
     */
    public OrderInfo(String userEmail, int orderNr, ArrayList<OrderItemInfo> orderItemInfo, OrderStatus status) {
        this.userEmail = userEmail;
        this.orderNr = orderNr;
        this.orderItemInfo = orderItemInfo;
        this.status = status;
    }

    /**
     * Constructs an {@code OrderInfo} object with the provided order details (without order number and status).
     * The order status is set to Pending by default.
     *
     * @param userEmail     the email of the user who placed the order
     * @param orderItemInfo the list of items in the order
     */
    public OrderInfo(String userEmail, ArrayList<OrderItemInfo> orderItemInfo) {
        this.userEmail = userEmail;
        this.orderItemInfo = orderItemInfo;
        this.status = OrderStatus.Pending;
    }

    // Getters
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

    // Setters
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setOrderItems(ArrayList<OrderItemInfo> orderItems) {
        this.orderItemInfo = orderItems;
    }

    @Override
    public String toString() {
        return "OrderInfo {" +
                "User email='" + userEmail + '\'' +
                ", Order nr='" + orderNr + '\'' +
                ", Status='" + status + '\'' +
                ", Order items=" + orderItemInfo.toString() + '\'' +
                '}';
    }
}